package br.com.halisson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.halisson")
public class DemoSpringTestcontainersRabbitmqCucumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringTestcontainersRabbitmqCucumberApplication.class, args);
	}

}
