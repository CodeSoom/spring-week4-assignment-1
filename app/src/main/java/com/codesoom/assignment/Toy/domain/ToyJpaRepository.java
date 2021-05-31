package com.codesoom.assignment.Toy.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@Primary
public interface ToyJpaRepository extends CrudRepository<Toy, Long> {

    Optional<Toy> findById(Long newId);

    Toy save(Toy toy);

    List<Toy> findAll();

    void delete(Toy toy);
}
