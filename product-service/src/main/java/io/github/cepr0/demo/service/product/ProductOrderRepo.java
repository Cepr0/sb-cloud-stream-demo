package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.model.product.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepo extends JpaRepository<ProductOrder, Long> {
}
