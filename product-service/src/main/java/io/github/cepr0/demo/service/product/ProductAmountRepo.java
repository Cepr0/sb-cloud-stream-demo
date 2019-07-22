package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.model.product.ProductAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAmountRepo extends JpaRepository<ProductAmount, Integer> {
}
