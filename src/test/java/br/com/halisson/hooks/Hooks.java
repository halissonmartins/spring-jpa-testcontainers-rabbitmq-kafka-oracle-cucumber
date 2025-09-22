package br.com.halisson.hooks;

import br.com.halisson.config.CucumberSpringConfiguration;
import io.cucumber.java.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO Not used
public class Hooks extends CucumberSpringConfiguration {

    static Logger log = LoggerFactory.getLogger(Hooks.class);
    /**
     * Precisa ser do Cucumber, pq ele quem esta fornecendo o contexto de teste
     */
    @BeforeAll
    public static void setup() {
        if (!rabbit.isRunning()) {
            rabbit.start();
        } else {
            log.info("\n#### RABBIT CONTAINER JÁ ESTA UP");
        }
        log.info("\n============================="
                + "\n######## CONTAINERS ESTAO UP"
                + "\n============================"
        );
    }
    /**
     * Precisa ser do Junit, pq ele que esta orquestrando os containers
     */
    @AfterAll
    public static void teardown() {
        if (rabbit.isRunning()) {
            rabbit.stop();
            log.info("\n#### RABBIT CONTAINER ESTA DOWN");
        }
        log.info("\n=============================="
                + "\n######## CONTAINERS ESTAO DOWN"
                + "\n============================="
        );
    }
}
