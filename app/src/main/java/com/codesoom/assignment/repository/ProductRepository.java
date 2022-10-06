package com.codesoom.assignment.repository;

import com.codesoom.assignment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Product repository.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
