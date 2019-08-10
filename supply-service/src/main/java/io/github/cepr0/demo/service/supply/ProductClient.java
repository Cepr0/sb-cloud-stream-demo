package io.github.cepr0.demo.service.supply;

import io.github.cepr0.demo.commons.TriFunction;
import io.github.cepr0.demo.commons.dto.RestockResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ProductClient {

	private final TriFunction<Integer, Integer, Integer, ResponseEntity<RestockResponse>> funRestock;
	private final AppProps props;

	public ProductClient(TriFunction<Integer, Integer, Integer, ResponseEntity<RestockResponse>> funRestock, AppProps props) {
		this.funRestock = funRestock;
		this.props = props;
	}

	public Optional<Integer> restock(int productId, int version) {
		int amount = props.getAmount();
		var response = funRestock.apply(productId, amount, version);
		return Optional.ofNullable(response.getBody()).map(
				restockResponse -> {
					log.info("[i] Received response: {}", restockResponse);
					return amount;
				})
				.or(() -> {
					log.error("[w] Response error: {}", response.getStatusCode());
					return Optional.empty();
				});
	}
}
