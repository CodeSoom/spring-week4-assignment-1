package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

public interface JPAProductRepostory extends CrudRepository<Product, Long> {
}
