package com.codesoom.assignment.product.infra.persistence;

import com.codesoom.assignment.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
