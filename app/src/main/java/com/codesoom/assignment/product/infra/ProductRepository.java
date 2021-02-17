package com.codesoom.assignment.product.infra;

import com.codesoom.assignment.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
