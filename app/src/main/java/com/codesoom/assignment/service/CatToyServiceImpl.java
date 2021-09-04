package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.common.exception.CatToyNotFoundException;
import com.codesoom.assignment.repository.CatToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        CatToy foundCatToy = catToyRepository.findById(id).orElseThrow(CatToyNotFoundException::new);
        foundCatToy.update(catToy);
        return catToyRepository.save(foundCatToy);
    }

    public void deleteCatToyById(Long id) {
        catToyRepository.deleteById(id);
    }
}
