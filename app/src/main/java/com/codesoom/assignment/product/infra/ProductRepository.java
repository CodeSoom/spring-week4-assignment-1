package com.codesoom.assignment.product.infra;

import com.codesoom.assignment.product.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
