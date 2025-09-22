package br.com.halisson.gherkin.steps;
import static org.junit.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ExampleStep {
	
	static Logger log = LoggerFactory.getLogger(ExampleStep.class);
	
    private String testString;
    private String resultString;

    @Given("I have a string with value {string}")
    public void i_have_a_string_with_value(String string) {
    	log.info("GIVEN: ");
        testString = string;
    }

    @When("I reverse the string")
    public void i_reverse_the_string() {
    	log.info("WHEN: ");
        resultString = new StringBuilder(testString).reverse().toString();
    }

    @Then("the result should be {string}")
    public void the_result_should_be(String expectedResult) {
    	log.info("THEN: ");
        assertEquals(expectedResult, resultString);
    }
}
