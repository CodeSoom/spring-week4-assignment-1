package com.codesoom.assignment.controllers;


import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class CatToyController {

    private CatToyService service;

    public CatToyController(CatToyService service) {
        this.service = service;
    }

    @GetMapping
    public List<CatToy> list() {
        return service.getToys();
    }

    @GetMapping("{id}")
    public CatToy detail(@PathVariable Long id) {
        return service.getDetailToy(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy create(@RequestBody CatToy catToy) {
        return service.createToy(catToy);
    }

    @PutMapping("{id}")
    public CatToy put(@PathVariable Long id, @RequestBody CatToy catToy) {
        return service.updateToy(id, catToy);
    }

    @PatchMapping("{id}")
    public CatToy patch(@PathVariable Long id, @RequestBody CatToy catToy) {
        return service.updateToy(id, catToy);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteToy(id);
    }
}
