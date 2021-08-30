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
        CatToy foundCatToy = catToyRepository.findById(id);
        foundCatToy.setImage(catToy.getImage());
        foundCatToy.setMaker(catToy.getMaker());
        foundCatToy.setPrice(catToy.getPrice());
        foundCatToy.setName(catToy.getName());

        return catToyRepository.save(foundCatToy);
    }

    public void deleteCatToyById(int id) {
        catToyRepository.deleteById(id);
    }
}
