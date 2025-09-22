package br.com.halisson.gherkin.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.halisson.config.CucumberSpringConfiguration;
import br.com.halisson.domain.PersonEntity;
import br.com.halisson.repository.PersonRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class OracleIntegrationSteps extends CucumberSpringConfiguration {
	
    @Autowired
    private PersonRepository personRepository;

    Logger log = LoggerFactory.getLogger(OracleIntegrationSteps.class);

    private PersonEntity person = new PersonEntity();

    @Given("oracle is up and running")
    public void oracle_is_up_and_running() {
        log.info("\n==================================="
                + "\n######## CONTAINER ORACLE ESTA UP"
                + "\n=================================="
        );
        Assertions.assertTrue(oracle.isRunning());
    }

    @When("save the person data in the database")
    public void save_the_person_data_in_the_database() {
        log.info("\n===================================="
                + "\n######## ORACLE - SALVANDO DADOS"
                + "\n==================================="
        );
        // Criar uma nova entidade Person
        person.setName("John Doe");

        // Salvar a entidade no banco de dados
        person = personRepository.save(person);
    }

    @Then("the person's data was saved with successfully")
    public void the_person_s_data_was_saved_with_successfully() {

        log.info("\n====================================="
                + "\n######## ORACLE - OBTENDO DADOS"
                + "\n===================================="
        );

        // Verificar se a entidade foi salva e possui um ID não nulo
        assertThat(person.getId()).isNotNull();

        // Recuperar a entidade do banco de dados
        PersonEntity foundPerson = personRepository.findById(person.getId()).orElse(null);

        // Verificar se a entidade foi recuperada com sucesso
        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getName()).isEqualTo("John Doe");
    }

}
