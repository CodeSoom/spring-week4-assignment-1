package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Toy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToyRepository extends CrudRepository<Toy, Long> {
}
