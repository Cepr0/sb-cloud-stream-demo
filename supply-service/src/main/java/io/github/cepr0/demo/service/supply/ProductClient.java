package io.github.cepr0.demo.service.supply;

import io.github.cepr0.demo.commons.dto.RestockResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.BiFunction;

@Slf4j
@Component
public class ProductClient {

	private final BiFunction<Integer, Integer, ResponseEntity<RestockResponse>> funRestock;
	private final AppProps props;

	public ProductClient(BiFunction<Integer, Integer, ResponseEntity<RestockResponse>> funRestock, AppProps props) {
		this.funRestock = funRestock;
		this.props = props;
	}

	public Optional<Integer> restock(int productId) {
		int amount = props.getAmount();
		var response = funRestock.apply(productId, amount);
		return Optional.ofNullable(response.getBody()).map(
				restockResponse -> {
					log.info("[i] Received response: {}", restockResponse);
					return amount;
				})
				.or(() -> {
					log.error("[!] Response error: {}", response.getStatusCode());
					return Optional.empty();
				});
	}
}
