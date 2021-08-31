package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    default Product save(Product product) { return product; }

    @Override
    default Optional<Product> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    default Iterable<Product> findAll() {
        return null;
    }

    @Override
    default void deleteById(Long aLong) {}

}
