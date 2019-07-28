package io.github.cepr0.demo.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "client")
public class AppProps {
	private Duration interval = Duration.ofSeconds(1);
}
