package com.codesoom.assignment.Toy.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@Primary
public interface ToyJpaRepository extends CrudRepository<Toy, Long> {

    @NonNull
    Optional<Toy> findById(@NonNull Long newId);

    @NonNull
    Toy save(@NonNull Toy toy);

    @NonNull
    List<Toy> findAll();

    void delete(@NonNull Toy toy);
}
