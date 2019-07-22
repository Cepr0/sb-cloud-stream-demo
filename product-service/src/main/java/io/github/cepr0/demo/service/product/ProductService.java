package io.github.cepr0.demo.service.product;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.util.Optional;

@Service
public class ProductService {

	private final ProductAmountRepo productAmountRepo;

	public ProductService(ProductAmountRepo productAmountRepo) {
		this.productAmountRepo = productAmountRepo;
	}

	@Transactional
	@Retryable(OptimisticLockException.class)
	public Optional<Integer> sell(int productId) {
		return productAmountRepo.findById(productId)
				.map(productAmount -> {
					int amount = productAmount.getAmount();
					if (amount > 0) {
						productAmount.setAmount(amount - 1);
					}
					return amount;
				});
	}
}
