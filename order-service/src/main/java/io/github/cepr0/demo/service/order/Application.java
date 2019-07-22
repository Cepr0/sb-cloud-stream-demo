package io.github.cepr0.demo.service.order;

import io.github.cepr0.demo.commons.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@EntityScan({
		"io.github.cepr0.demo.commons.model.base",
		"io.github.cepr0.demo.commons.model.order"
})
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

