package com.codesoom.assignment.repositories;

import com.codesoom.assignment.models.Toy;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ToyRepository extends CrudRepository<Toy, Long> {
    List<Toy> findAll();

    Optional<Toy> findById(long id);

    Toy save(Toy toy);

    void modify(long id, Toy toy);

    void deleteById(long id);
}
