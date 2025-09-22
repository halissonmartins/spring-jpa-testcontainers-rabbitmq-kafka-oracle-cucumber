package br.com.halisson.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.halisson.consumer.RabbitMQConsumer;
import br.com.halisson.producer.RabbitMQProducer;

@Configuration
public class RabbitMqConfiguration {

    public static final String QUEUE_NAME = "queue-name";
    public static final String EXCHANGE_NAME = "exchange-name";
    public static final String ROUTING_KEY = "routing-key";

    // Declara a fila
    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, true); // durable = true
    }

    // Declara o exchange
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Faz o binding da fila com o exchange
    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // Template para enviar mensagens
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    // Listener simples (consumidor)
    @Bean
    SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                                  MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    
//    @Bean
//    RabbitMQProducer rabbitMQProducer(RabbitTemplate rabbitTemplate) {
//        return new RabbitMQProducer(rabbitTemplate);
//    }

    // Adaptador que conecta o listener a um método específico
    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMQConsumer consumer) {
        return new MessageListenerAdapter(consumer, "receiveMessage");
    }
}

