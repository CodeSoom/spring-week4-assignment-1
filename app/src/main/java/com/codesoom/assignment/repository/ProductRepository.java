package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
