package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Long, Product> {
}
