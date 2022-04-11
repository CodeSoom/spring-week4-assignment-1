package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class CatToyController {

    private final CatToyService catToyService;

    public CatToyController(CatToyService catToyService) {
        this.catToyService = catToyService;
    }

    @GetMapping
    public List<CatToy> list() {
        return catToyService.getCatToys();
    }
}
