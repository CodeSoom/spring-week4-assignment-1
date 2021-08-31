package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import com.codesoom.assignment.service.CatToyService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<CatToy> getCatToys() {
        return catToyService.getCatToys();
    }

    @GetMapping("/{id}")
    public CatToy findCatToyById(@PathVariable int id) {
        return catToyService.findCatToyById(id).orElseThrow(CatToyNotFoundException::new);
    }

    @PostMapping
    public CatToy registerCatToy(@RequestBody CatToy catToy) {
        return catToyService.addCatToy(catToy);
    }

    @PatchMapping("/{id}")
    public CatToy updateCatToy(@PathVariable int id, @RequestBody CatToy catToy) {
        return catToyService.updateCatToy(id, catToy);
    }

    @DeleteMapping("/{id}")
    public void deleteCatToy(@PathVariable int id) {
        catToyService.deleteCatToyById(id);
    }
}
