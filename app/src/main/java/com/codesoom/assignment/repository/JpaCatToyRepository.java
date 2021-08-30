package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.CatToy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCatToyRepository extends CatToyRepository, CrudRepository<CatToy, Long> {
    List<CatToy> findAll();
    CatToy findById(int id);
    CatToy save(CatToy catToy);
    CatToy updateCatToy(int id, CatToy catToy);
    CatToy delete(int id);
}
