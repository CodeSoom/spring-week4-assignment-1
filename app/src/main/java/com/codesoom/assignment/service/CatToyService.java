package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.repository.CatToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatToyService {

    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public List<CatToy> getCatToys() {
        return catToyRepository.findAll();
    }

    public CatToy findCatToyById(int id) {
        return catToyRepository.findById(id);
    }

    public CatToy addCatToy(CatToy catToy) {
        return catToyRepository.save(catToy);
    }

    public CatToy updateCatToy(int id, CatToy catToy) {
        return catToyRepository.updateCatToy(id, catToy);
    }

    public CatToy deleteCatToy(int id) {
        return catToyRepository.delete(id);
    }
}
