package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductJPARepository extends ProductRepository, CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();

    @Override
    Optional<Product> find(Long id);

    @Override
    Product save(Product product);

    @Override
    void deleteById(Long id);
}
