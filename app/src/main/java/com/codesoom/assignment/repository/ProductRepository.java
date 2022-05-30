package com.codesoom.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesoom.assignment.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

