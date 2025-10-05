package br.com.halisson;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import br.com.halisson.config.CucumberSpringConfiguration;
import br.com.halisson.consumer.KafkaConsumer;
import br.com.halisson.producer.KafkaProducer;

@SpringBootTest
@DirtiesContext
public class KafkaIntegrationTest extends CucumberSpringConfiguration {

    static Logger log = LoggerFactory.getLogger(KafkaIntegrationTest.class);

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;
    
    private String data;

    @Test
    public void testSendAndReceiveMessageContainer() throws InterruptedException {

        log.info("\n===================================="
                + "\n######## KAFKA - ENVIANDO A MENSAGEM"
                + "\n==================================="
        );

        data = "Sending with our own simple KafkaProducer";
        producer.send("embedded-test-topic", data);

        log.info("\n====================================="
                + "\n######## KAFKA - RECEBENDO MENSAGEM2"
                + "\n===================================="
        );
        // Esperar até que a mensagem seja recebida ou o tempo limite seja atingido
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        // Verifica se a mensagem foi recebida com sucesso
        Assertions.assertTrue(messageConsumed);
        assertThat(consumer.getPayload(), containsString(data));
    }
}
