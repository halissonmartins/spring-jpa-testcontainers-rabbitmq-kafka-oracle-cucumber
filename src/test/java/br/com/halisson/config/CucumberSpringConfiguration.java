package br.com.halisson.config;

import org.springframework.test.context.ActiveProfiles;

import io.cucumber.spring.CucumberContextConfiguration;

@ActiveProfiles("test")
@CucumberContextConfiguration
public class CucumberSpringConfiguration extends ContainersConfiguration {

}
