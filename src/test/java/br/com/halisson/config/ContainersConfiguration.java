package br.com.halisson.config;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
public abstract class ContainersConfiguration {

    static Logger log = LoggerFactory.getLogger(ContainersConfiguration.class);
    
    @Container
    public static KafkaContainer kafka;

    @Container
    public static RabbitMQContainer rabbit;
    
    @Container
    public static OracleContainer oracle;

    static String DOCKER_KAFKA_IMAGE_NAME = "confluentinc/cp-kafka:7.4.0";
    static String DOCKER_RABBIT_IMAGE_NAME = "rabbitmq:3";
    static String DOCKER_ORACLE_IMAGE_NAME = "gvenzl/oracle-xe:21-slim-faststart";
    
    static {
    	
        log.info("\n============================"
                + "\n######## CRIANDO IMAGENS"
                + "\n============================"
        );

        //TODO use the new class
        kafka = new KafkaContainer(DockerImageName.parse(DOCKER_KAFKA_IMAGE_NAME));
        
        rabbit = new RabbitMQContainer(DOCKER_RABBIT_IMAGE_NAME);
       
        oracle = new OracleContainer(DOCKER_ORACLE_IMAGE_NAME)
                .withDatabaseName("testDB");
        
        // Inicia os containers antes dos testes
        Startables.deepStart(Stream.of(kafka, rabbit, oracle)).join();

    }

    public static String securityProtocol() {
        return "PLAINTEXT";
    }

    @DynamicPropertySource
    public static void dynamicPropertySource(final DynamicPropertyRegistry registry) {
        log.info("\n============================"
                + "\n######## INICIEI AS CONFIG"
                + "\n============================"
        );
        
        //RabbitMQ
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbit::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbit::getAdminPassword);
        
        //Kafka
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.properties.schema.registry.url", kafka::getHost);
        registry.add("spring.kafka.consumer.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("kafka.security.protocol", ContainersConfiguration::securityProtocol);
        
        //Oracle
        registry.add("spring.datasource.url", oracle::getJdbcUrl);
        registry.add("spring.datasource.username", oracle::getUsername);
        registry.add("spring.datasource.password", oracle::getPassword);
    }
}
