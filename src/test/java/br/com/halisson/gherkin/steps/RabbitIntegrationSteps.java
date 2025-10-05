package br.com.halisson.gherkin.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.halisson.config.CucumberSpringConfiguration;
import br.com.halisson.consumer.RabbitMQConsumer;
import br.com.halisson.producer.RabbitMQProducer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RabbitIntegrationSteps extends CucumberSpringConfiguration {

    static Logger log = LoggerFactory.getLogger(RabbitIntegrationSteps.class);

    @Autowired
    private RabbitMQProducer producer;

    @Autowired
    private RabbitMQConsumer consumer;

    private static String data = "Sending with our own simple RabbitMQ Producer";

	@Given("rabbit is up and running")
    public void rabbitIsUpAndRunning() {
        log.info("\n================================="
                + "\n######## CONTAINER RABBITMQ ESTA UP"
                + "\n================================"
        );
        Assertions.assertTrue(rabbit.isRunning());
    }

    @When("a message is sent to a rabbit queue")
    public void sendMessageToRabbitQueue() throws InterruptedException {
        log.info("\n============================="
                + "\n######## ENVIANDO A MENSAGEM"
                + "\n============================"
        );
        
        producer.sendMessage(data);
    }

    @Then("the message should be received successfully in a rabbit consumer")
    public void verifyMessageReceivedSuccessfully() throws InterruptedException {
        log.info("\n=============================="
                + "\n######## RECEBENDO A MENSAGEM"
                + "\n============================="
        );
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        Assertions.assertTrue(messageConsumed);
        assertThat(consumer.getReceivedMessage(), containsString(data));
    }
}
