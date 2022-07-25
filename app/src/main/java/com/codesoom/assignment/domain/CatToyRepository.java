package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CatToyRepository extends CrudRepository<CatToy, Long>{

    List<CatToy> findAll();
    Optional<CatToy> findById(Long id);

}
