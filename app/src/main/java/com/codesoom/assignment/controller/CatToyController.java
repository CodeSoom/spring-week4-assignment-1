package com.codesoom.assignment.controller;

import com.codesoom.assignment.common.exception.CatToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.service.CatToyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class CatToyController {

    private final CatToyService catToyService;

    public CatToyController(CatToyService catToyService) {
        this.catToyService = catToyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CatToy> getCatToys() {
        return catToyService.getCatToys();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CatToy findCatToyById(@PathVariable Long id) {
        return catToyService.findCatToyById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy registerCatToy(@RequestBody CatToy catToy) {
        return catToyService.addCatToy(catToy);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CatToy updateCatToy(@PathVariable Long id, @RequestBody CatToy catToy) {
        return catToyService.updateCatToy(id, catToy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatToy(@PathVariable Long id) {
        catToyService.deleteCatToyById(id);
    }
}
