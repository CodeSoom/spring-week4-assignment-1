package com.codesoom.assignment.domain.repository;

import com.codesoom.assignment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    void deleteById(Long id);
}
