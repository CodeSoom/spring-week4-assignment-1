package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToyRepository extends CrudRepository<Toy, Long> {
    List<Toy> findAll();

    Optional<Toy> findById(Long id);

    Toy save(Toy toy);

    void delete(Toy toy);
}
