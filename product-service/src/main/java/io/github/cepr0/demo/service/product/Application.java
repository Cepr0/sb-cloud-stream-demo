package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ProductRepo productRepo() {
		return new ProductRepo();
	}
}
