package com.codesoom.assignment.repository;

import com.codesoom.assignment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToyStoreRepository extends JpaRepository<Product, Long> {

    Product save(Product product);
}
