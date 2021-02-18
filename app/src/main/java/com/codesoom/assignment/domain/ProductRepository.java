package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.request.ProductRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository
        extends CrudRepository<Product, Long> {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(ProductRequest request);
}
