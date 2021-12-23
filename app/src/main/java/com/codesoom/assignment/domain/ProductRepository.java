package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    @Nonnull
    List<Product> findAll();
}
