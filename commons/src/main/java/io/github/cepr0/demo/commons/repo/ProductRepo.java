package io.github.cepr0.demo.commons.repo;

import io.github.cepr0.demo.commons.model.product.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ProductRepo {

	private static final int INITIAL_AMOUNT = 20;

	// Product and its amount
	private final Map<Product, Integer> products = new ConcurrentHashMap<>();

	public ProductRepo() {
		products.putAll(Map.of(
				new Product(1, "Product 1"), INITIAL_AMOUNT,
				new Product(2, "Product 2"), INITIAL_AMOUNT,
				new Product(3, "Product 3"), INITIAL_AMOUNT,
				new Product(4, "Product 4"), INITIAL_AMOUNT,
				new Product(5, "Product 5"), INITIAL_AMOUNT,
				new Product(6, "Product 6"), INITIAL_AMOUNT,
				new Product(7, "Product 7"), INITIAL_AMOUNT,
				new Product(8, "Product 8"), INITIAL_AMOUNT,
				new Product(9, "Product 9"), INITIAL_AMOUNT,
				new Product(10, "Product 10"), INITIAL_AMOUNT
		));
	}

	public Optional<Product> get(int id) {
		return products.keySet()
				.stream()
				.filter(product -> product.getId() == id)
				.findFirst();
	}

	public Optional<Integer> getAmount(int id) {
		return get(id).map(products::get);
	}

	public Optional<Product> sell(int id) {

		return get(id).map(p -> {
			int amount = products.get(p);
			if (amount != 0) {
				int rest = amount - 1;
				products.replace(p, rest);
				log.info("[i] {} rest amount is {}" , p, rest);
				return p;
			}
			return null;
		});
	}

	public Optional<Product> fillUp(int id, int qnt) {
		return get(id).map(p -> {
			int amount = products.get(p);
			products.replace(p, amount + qnt);
			return p;
		});
	}

	public int count() {
		return products.size();
	}
}
