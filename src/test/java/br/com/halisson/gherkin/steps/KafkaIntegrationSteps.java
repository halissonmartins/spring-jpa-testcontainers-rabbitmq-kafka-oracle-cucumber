package br.com.halisson.gherkin.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import br.com.halisson.config.CucumberSpringConfiguration;
import br.com.halisson.consumer.KafkaConsumer;
import br.com.halisson.producer.KafkaProducer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext
public class KafkaIntegrationSteps extends CucumberSpringConfiguration {

    static Logger log = LoggerFactory.getLogger(KafkaIntegrationSteps.class);

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    private static String data = "Sending with our own simple KafkaProducer";

	@Given("Kafka is up and running")
    public void kafkaIsUpAndRunning() {
        log.info("\n================================="
                + "\n######## CONTAINER KAFKA ESTA UP"
                + "\n================================"
        );
        Assertions.assertTrue(kafka.isRunning());
    }

    @When("a message is sent to a Kafka topic")
    public void sendMessageToKafkaTopic() throws InterruptedException {
        log.info("\n============================="
                + "\n######## ENVIANDO A MENSAGEM"
                + "\n============================"
        );
        
        producer.send("embedded-test-topic", data);
    }

    @Then("the message should be received successfully in a kafka consumer")
    public void verifyMessageReceivedSuccessfully() throws InterruptedException {
        log.info("\n=============================="
                + "\n######## RECEBENDO A MENSAGEM"
                + "\n============================="
        );
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        Assertions.assertTrue(messageConsumed);
        assertThat(consumer.getPayload(), containsString(data));
    }
}
