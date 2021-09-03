package com.codesoom.assignment.domains;

import com.codesoom.assignment.ProductNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    void delete(Product product);
}
