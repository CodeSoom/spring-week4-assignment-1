package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.CatToy;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CatToyRepository extends CrudRepository<CatToy, Long> {
    List<CatToy> findAll();
    Optional<CatToy> findById(Long id);
    CatToy save(CatToy catToy);
    void deleteById(Long id);
}
