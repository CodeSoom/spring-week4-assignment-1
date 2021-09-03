package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findAll();

  Optional<Product> findById(Long id);

  Product save(Product product);

  void delete(Product product);}
