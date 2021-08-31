package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.CatToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.repository.CatToyRepository;

import java.util.List;
import java.util.Optional;

public interface CatToyService {
    List<CatToy> getCatToys();
    Optional<CatToy> findCatToyById(int id);
    CatToy addCatToy(CatToy catToy);
    CatToy updateCatToy(int id, CatToy catToy);
    void deleteCatToyById(int id);
}
