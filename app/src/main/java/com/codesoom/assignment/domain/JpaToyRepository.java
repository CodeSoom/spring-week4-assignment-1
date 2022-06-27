
package com.codesoom.assignment.domain;

import com.codesoom.assignment.domain.interfaces.ToyRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Primary
public interface JpaToyRepository
        extends ToyRepository, CrudRepository<Toy, Long> {
    List<Toy> findAll();

    Optional<Toy> findById(Long id);

    Toy save(Toy product);

    void delete(Toy product);
}
