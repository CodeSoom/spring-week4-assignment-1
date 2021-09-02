package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;

import java.util.List;
import java.util.Optional;

public interface CatToyService {
    List<CatToy> getCatToys();
    Optional<CatToy> findCatToyById(long id);
    CatToy addCatToy(CatToy catToy);
    CatToy updateCatToy(long id, CatToy catToy);
    void deleteCatToyById(long id);
}
