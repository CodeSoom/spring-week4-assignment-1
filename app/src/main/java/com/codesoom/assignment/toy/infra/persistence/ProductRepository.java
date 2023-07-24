package com.codesoom.assignment.toy.infra.persistence;

import com.codesoom.assignment.toy.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface ProductRepository extends CrudRepository<Product, Long> {
}
