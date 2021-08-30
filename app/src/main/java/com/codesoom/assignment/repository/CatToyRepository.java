package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.CatToy;

import java.util.List;

public interface CatToyRepository {
    List<CatToy> findAll();
    CatToy findById(int id);
    CatToy save(CatToy catToy);
    CatToy updateCatToy(int id, CatToy catToy);
    CatToy delete(int id);
}
