
package com.codesoom.assignment.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Primary
public interface JpaProducerRepository
        extends CrudRepository<ToyProducer, Long> {
    List<ToyProducer> findAll();

    Optional<ToyProducer> findById(Long id);
}
