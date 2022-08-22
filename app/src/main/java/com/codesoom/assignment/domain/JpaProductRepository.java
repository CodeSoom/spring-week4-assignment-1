package com.codesoom.assignment.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

@Primary
public interface JpaProductRepository extends ProductRepository, CrudRepository<Product, Long> {

    Collection<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product toy);

    void delete(Product toy);
}
