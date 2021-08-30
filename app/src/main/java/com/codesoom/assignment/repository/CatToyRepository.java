package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.CatToy;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CatToyRepository extends CrudRepository<CatToy, Long> {
    List<CatToy> findAll();
    CatToy findById(int id);
    CatToy save(CatToy catToy);
    void deleteById(int id);
}
