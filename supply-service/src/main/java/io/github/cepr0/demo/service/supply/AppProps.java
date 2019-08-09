package io.github.cepr0.demo.service.supply;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "supply")
public class AppProps {

	/**
	 * Default supply amount.
	 */
	private int amount = 10;
}
