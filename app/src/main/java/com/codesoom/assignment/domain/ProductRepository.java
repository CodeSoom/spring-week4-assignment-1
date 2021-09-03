package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


  List<Product> findAll();


  Optional<Product> findCatProductBy(Long id);
}
