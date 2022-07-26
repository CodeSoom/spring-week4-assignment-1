package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cattoys")
@CrossOrigin
public class CatToyController {
    private CatToyService catToyService;

    public CatToyController(CatToyService catToyService){
        this.catToyService = catToyService;
    }

    @GetMapping
    public List<CatToy> list(){
        return catToyService.getCatToys();
    }

    @GetMapping("{id}")
    public CatToy detail(@PathVariable Long id){
        return catToyService.getToys(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy update(@RequestBody CatToy source){
        return catToyService.createToy(source);
    }

    @PutMapping("{id}")
    public CatToy update(@PathVariable Long id, @RequestBody CatToy source){
        return catToyService.updateToy(id, source);
    }

    @PatchMapping("{id}")
    public CatToy patch(@PathVariable Long id, @RequestBody CatToy source){
        return catToyService.updateToy(id, source);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        catToyService.deleteToy(id);
    }
}
