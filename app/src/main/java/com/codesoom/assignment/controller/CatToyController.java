package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class CatToyController {

    private final CatToyRepository catToyRepository;

    public CatToyController(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    @GetMapping
    public List<CatToy> list() {
        Iterable<CatToy> source = catToyRepository.findAll();

        List<CatToy> catToys = new ArrayList<>();

        source.forEach(catToys::add);

        return catToys;
    }
}
