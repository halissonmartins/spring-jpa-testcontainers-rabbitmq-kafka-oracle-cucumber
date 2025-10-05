package br.com.halisson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import br.com.halisson.config.CucumberSpringConfiguration;
import br.com.halisson.consumer.RabbitMQConsumer;
import br.com.halisson.producer.RabbitMQProducer;

@SpringBootTest
@DirtiesContext
public class RabbitMQIntegrationTest extends CucumberSpringConfiguration {

    static Logger log = LoggerFactory.getLogger(RabbitMQIntegrationTest.class);

    @Autowired
    private RabbitMQProducer producer;

    @Autowired
    private RabbitMQConsumer consumer;

    private static final CountDownLatch latch = new CountDownLatch(1);
    private static String receivedMessage;

    @Test
    public void testSendAndReceiveMessage() throws InterruptedException {
        log.info("\n===================================="
                + "\n######## RABBIT - ENVIANDO MENSAGEM"
                + "\n==================================="
        );
        // Mensagem a ser enviada
        String message = "Hello RabbitMQ!";
        log.info("Mensagem enviada: {}", message);
        // Enviar a mensagem
        producer.sendMessage(message);

        // Simula o recebimento da mensagem no consumidor
        RabbitMQConsumer customConsumer = new RabbitMQConsumer() {
            @Override
            public void receiveMessage(String msg) {
                receivedMessage = msg;
                latch.countDown();  // Decrementa o latch
            }
        };

        log.info("\n====================================="
                + "\n######## RABBIT - RECEBENDO MENSAGEM"
                + "\n===================================="
        );

        // Registra o listener
        customConsumer.receiveMessage(message);

        // Espera até que a mensagem seja recebida ou o tempo limite seja atingido
        boolean messageReceived = latch.await(10, TimeUnit.SECONDS);

        // Verifica se a mensagem foi recebida com sucesso
        assertTrue(messageReceived);
        assertEquals(receivedMessage, message);
    }

    @Test
    public void testSendAndReceiveMessageContainer() throws InterruptedException {

        log.info("\n===================================="
                + "\n######## RABBIT - ENVIANDO MENSAGEM2"
                + "\n==================================="
        );
        // Mensagem a ser enviada
        String message = "Hello RabbitMQ!";

        log.info("Mensagem enviada: {}", message);
        // Enviar a mensagem
        producer.sendMessage(message);

        log.info("\n====================================="
                + "\n######## RABBIT - RECEBENDO MENSAGEM2"
                + "\n===================================="
        );
        // Esperar até que a mensagem seja recebida ou o tempo limite seja atingido
        boolean messageReceived = consumer.getLatch().await(10, TimeUnit.SECONDS);
        log.info("Mensagem recebida: {}", consumer.getReceivedMessage());

        // Verificar se a mensagem foi recebida com sucesso
        assertTrue(messageReceived, "A mensagem não foi recebida dentro do tempo limite");
        assertEquals(message, consumer.getReceivedMessage(), "A mensagem recebida não corresponde à mensagem enviada");
    }
}
