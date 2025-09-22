package br.com.halisson.consumer;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.halisson.producer.RabbitMQProducer;
import lombok.Getter;

@Getter
@Component
public class RabbitMQConsumer {
	
	static Logger log = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final CountDownLatch latch = new CountDownLatch(1);
    private String receivedMessage;

    @RabbitListener(queues = "queue-name")
    public void receiveMessage(String message) {
        this.receivedMessage = message;
        latch.countDown();  // Sinaliza que a mensagem foi recebida
    }

}
