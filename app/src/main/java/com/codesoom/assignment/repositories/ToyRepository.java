package com.codesoom.assignment.repositories;

import com.codesoom.assignment.models.Toy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToyRepository extends CrudRepository<Toy, Long> {
    @Override
    List<Toy> findAll();

    @Override
    Optional<Toy> findById(Long id);

    @Override
    Toy save(Toy toy);

    @Override
    void deleteById(Long id);
}
