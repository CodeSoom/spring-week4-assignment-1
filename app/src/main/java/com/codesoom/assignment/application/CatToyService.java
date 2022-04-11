package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatToyService {

    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public List<CatToy> getCatToys() {
        Iterable<CatToy> source = catToyRepository.findAll();
        List<CatToy> catToys = new ArrayList<>();
        source.forEach(catToys::add);
        return catToys;
    }
}
