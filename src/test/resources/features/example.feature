Feature: Exemplo de Teste de Integração

  @teste  @validado
  Scenario: Test integration with CUCUMBER
    Given I have a string with value "hello"
    When I reverse the string
    Then the result should be "olleh"

  @rabbit  @validado
  Scenario: Sending and receiving a message through RABBIT
    Given rabbit is up and running
    When a message is sent to a rabbit queue
    Then the message should be received successfully in a rabbit consumer

  @kafka  @validado
  Scenario: Sending and receiving a message through KAFKA
    Given Kafka is up and running
    When a message is sent to a Kafka topic
    Then the message should be received successfully in a kafka consumer
    
	@database  @validado
	Scenario: Save and getting a information about person
	  Given oracle is up and running
	  When save the person data in the database
	  Then the person's data was saved with successfully