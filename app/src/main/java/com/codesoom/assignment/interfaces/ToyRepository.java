package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.domain.Toy;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ToyRepository extends CrudRepository<Toy, Long> {
    List<Toy> findAll();

    Optional<Toy> findById(Long id);

    Toy save(Toy toy);

    void deleteById(Long id);

    boolean existsById(Long id);
}
