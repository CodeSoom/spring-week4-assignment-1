package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.exception.CatToyNotFoundException;
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

    public Optional<CatToy> findCatToyById(int id) {
        return Optional.ofNullable(catToyRepository.findById(id));
    }

    public CatToy addCatToy(CatToy catToy) {
        return catToyRepository.save(catToy);
    }

    public CatToy updateCatToy(int id, CatToy catToy) {
        Optional<CatToy> foundCatToy = Optional.ofNullable(catToyRepository.findById(id));
        foundCatToy.orElseThrow(CatToyNotFoundException::new);

        foundCatToy.get().setImage(catToy.getImage());
        foundCatToy.get().setMaker(catToy.getMaker());
        foundCatToy.get().setPrice(catToy.getPrice());
        foundCatToy.get().setName(catToy.getName());
        return catToyRepository.save(foundCatToy.get());
    }

    public void deleteCatToyById(int id) {
        catToyRepository.deleteById(id);
    }
}
