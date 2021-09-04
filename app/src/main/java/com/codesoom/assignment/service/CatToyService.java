package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;

import java.util.List;

public interface CatToyService {
    List<CatToy> getCatToys();
    CatToy findCatToyById(Long id);
    CatToy addCatToy(CatToy catToy);
    CatToy updateCatToy(Long id, CatToy catToy);
    CatToy deleteCatToyById(Long id);
}
