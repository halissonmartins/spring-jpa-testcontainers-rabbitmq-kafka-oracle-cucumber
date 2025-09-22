package br.com.halisson;

import org.springframework.boot.SpringApplication;

public class TestDemoSpringTestcontainersRabbitmqCucumberApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoSpringTestcontainersRabbitmqCucumberApplication::main)
			.with(TestcontainersConfiguration.class)
			.run(args);
	}

}
