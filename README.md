# Description

This project is a minimal integration test example that demonstrates how to combine:
- JDK 25 
- Spring Boot – as the main application framework
- Spring JPA and Oracle – for database integration
- Spring Kafka – for producing and consuming Kafka messages
- Spring RabbitMQ – for producing and consuming RabbitMQ messages
- Testcontainers – to run a real Kafka broker in a Docker container during tests
- Cucumber + JUnit 5 – for behavior-driven testing (BDD) with executable specifications

## Instructions
- Set JAVA_HOME with JDK 25
- Just run in terminal
	```
	mvn clean install
	```

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.6/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.6/maven-plugin/build-image.html)
* [Spring for RabbitMQ Streams](https://docs.spring.io/spring-amqp/reference/stream.html)
* [Spring for Kafka](https://docs.spring.io/spring-kafka/reference/index.html)

- https://medium.com/@uilenlelles/integra%C3%A7%C3%A3o-springboot-cucumber-junit-5-328739384d03
- https://medium.com/@uilenlelles/testcontainers-com-springboot-cucumber-junit-5-kafka-d455d18f9d4d
- https://medium.com/@uilenlelles/testcontainers-com-springboot-cucumber-junit-5-rabbitmq-814d3ee48f38
- https://medium.com/@uilenlelles/testcontainers-com-springboot-cucumber-junit-5-oracledatabase-6ed5d081f0bc

## Future Fixes
* Use recent versions of containers e libs
* Use Hooks class and Add containers Kafka and Oracle
* Use English in comments and logs

## Future Features
* Add two controllers (RabbitMQ and Kafka) that send a menssage and save in Oracle database
* Add Docker Compose file to run the application