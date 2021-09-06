package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.CatToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.repository.CatToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatToyServiceImpl implements CatToyService{

    private final CatToyRepository catToyRepository;

    public CatToyServiceImpl(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public List<CatToy> getCatToys() {
        return catToyRepository.findAll();
    }

    public CatToy findCatToyById(Long id) {
        return catToyRepository.findById(id).orElseThrow(CatToyNotFoundException::new);
    }

    public CatToy addCatToy(CatToy catToy) {
        return catToyRepository.save(catToy);
    }

    public CatToy updateCatToy(Long id, CatToy catToy) {
        CatToy foundCatToy = findCatToyById(id);
        foundCatToy.update(catToy);
        return foundCatToy;
    }

    public CatToy deleteCatToyById(Long id) {
        CatToy foundCatToy = catToyRepository.findById(id).orElseThrow(CatToyNotFoundException::new);
        catToyRepository.deleteById(id);
        return foundCatToy;
    }
}
