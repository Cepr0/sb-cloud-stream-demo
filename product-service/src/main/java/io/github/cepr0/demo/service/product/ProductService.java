package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.model.product.Product;
import io.github.cepr0.demo.commons.model.product.ProductAmount;
import io.github.cepr0.demo.commons.model.product.ProductOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

	private final ProductRepo productRepo;
	private final ProductAmountRepo productAmountRepo;
	private final ProductOrderRepo productOrderRepo;

	public ProductService(
			ProductRepo productRepo,
			ProductAmountRepo productAmountRepo,
			ProductOrderRepo productOrderRepo
	) {
		this.productRepo = productRepo;
		this.productAmountRepo = productAmountRepo;
		this.productOrderRepo = productOrderRepo;
	}

	@Transactional
	@Retryable(value = PersistenceException.class, maxAttempts = 5, backoff = @Backoff(delay = 100, multiplier = 1.5))
	public Optional<ProductAmount> sell(long orderId, int productId) {
		log.info("[i] Selling one product #{} with order #{}", productId, orderId);
		return productAmountRepo.findById(productId)
				.map(productAmount -> {
					int amount = productAmount.getAmount();
					if (amount > 0) {
						productAmount.setAmount(amount - 1);
					}
					Product product = productRepo.getOne(productId);
					productOrderRepo.save(new ProductOrder(orderId, product));
					return productAmount;
				});
	}

	@Transactional
	@Retryable(value = PersistenceException.class, maxAttempts = 5, backoff = @Backoff(delay = 100, multiplier = 1.5))
	public Optional<Integer> restock(int productId, int version, int amount) {
		log.info("[i] Restocking the product #{} with {}", productId, amount);
		return productAmountRepo.findByIdAndVersion(productId, version)
				.map(productAmount -> {
					int newAmount = productAmount.getAmount() + amount;
					productAmount.setAmount(newAmount);
					return newAmount;
				});
	}

	@Recover
	public Optional<ProductAmount> recoverSell(PersistenceException e, long orderId, int productId) {
		log.info("[w] Selling the product '{}' for order '{}' is failed because of {}", productId, orderId, e.toString());
		return Optional.empty();
	}

	@Recover
	public Optional<Integer> recoverRestock(PersistenceException e, int productId, int version, int amount) {
		log.info("[w] Restocking the product '{}' with amount '{}' and {} is failed because of {}", productId, amount, version, e.toString());
		return Optional.empty();
	}
}
