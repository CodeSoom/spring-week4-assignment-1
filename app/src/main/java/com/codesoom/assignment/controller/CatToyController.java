package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class CatToyController {
    private CatToyService catToyService;

    @Autowired
    CatToyController(CatToyService catToyService) {
        this.catToyService = catToyService;
    }

    @GetMapping
    List<CatToy> list() {
        return catToyService.findAllCatToys();
    }

    @GetMapping("{id}")
    CatToy detail(@PathVariable Long id) {
        return catToyService.findCatToy(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CatToy create(@RequestBody CatToy catToy) {
        return catToyService.createCatToy(catToy);
    }

    @PatchMapping("{id}")
    CatToy patch(@PathVariable Long id, @RequestBody CatToy catToy) {
        return catToyService.updateCatToy(id, catToy);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        catToyService.deleteCatToy(id);
    }
}
