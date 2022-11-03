package com.codesoom.assignment.products.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository
        extends ProductRepository, JpaRepository<Product, Long> {
}
