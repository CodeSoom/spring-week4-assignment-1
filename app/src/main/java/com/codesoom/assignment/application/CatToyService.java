package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatToyService {

    private CatToyRepository catToyRepository;

    @Autowired
    CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public List<CatToy> findAllCatToys() {
        return catToyRepository.findAll();
    }

    public CatToy findCatToy(Long id) {
        return catToyRepository.findById(id).orElseThrow(() -> new CatToyNotFoundException(id));
    }

    public void deleteCatToy(Long id) {
        try {
            catToyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CatToyNotFoundException(id);
        }
    }

    public CatToy createCatToy(CatToy catToy) {
        return catToyRepository.save(catToy);
    }

    public CatToy updateCatToy(Long id, CatToy source) {
        CatToy catToy = catToyRepository.findById(id).orElseThrow(() -> new CatToyNotFoundException(id));
        catToy.setImageUrl(source.getImageUrl());
        catToy.setName(source.getName());
        catToy.setMaker(source.getMaker());
        catToy.setPrice(source.getPrice());

        CatToy updatedCatToy = catToyRepository.save(catToy);
        return updatedCatToy;
    }
}
