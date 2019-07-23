package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.model.product.Product;
import io.github.cepr0.demo.commons.model.product.ProductOrder;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.util.Optional;

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
	@Retryable(OptimisticLockException.class)
	public Optional<Integer> sell(long orderId, int productId) {
		return productAmountRepo.findById(productId)
				.map(productAmount -> {
					int amount = productAmount.getAmount();
					if (amount > 0) {
						productAmount.setAmount(amount - 1);
					}
					Product product = productRepo.getOne(productId);
					productOrderRepo.save(new ProductOrder(orderId, product));
					return amount;
				});
	}
}
