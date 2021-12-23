package com.codesoom.assignment.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
public interface CatToyRepository extends CrudRepository<CatToy, Long> {

    CatToy save(CatToy catToy);

    Optional<CatToy> findById(Long id);

    List<CatToy> findAll();

    void deleteById(Long id);
}
