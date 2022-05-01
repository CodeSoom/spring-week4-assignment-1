package com.codesoom.assignment.repositories;

import com.codesoom.assignment.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
