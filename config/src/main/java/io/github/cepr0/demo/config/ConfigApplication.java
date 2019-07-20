package io.github.cepr0.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@Slf4j
@EnableBinding(Routes.class)
@SpringBootApplication
public class ConfigApplication {

	public static void main(String[] args) {
		SpringApplication
				.run(ConfigApplication.class, args)
				.close();
	}
}
