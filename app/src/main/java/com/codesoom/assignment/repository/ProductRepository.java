package com.codesoom.assignment.repository;

import com.codesoom.assignment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
