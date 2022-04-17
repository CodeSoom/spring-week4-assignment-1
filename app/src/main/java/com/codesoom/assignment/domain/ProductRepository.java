package com.codesoom.assignment.domain;


import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Product product);

    void deleteAll();

    long count();

    @Query(value = "SELECT nextval('item_id_seq')", nativeQuery = true)
    Long getSequenceValue();
}
