package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
