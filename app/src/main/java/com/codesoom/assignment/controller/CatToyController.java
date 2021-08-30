package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


/**
 * 고양이 장난감에 대한 Http Request 요청 컨트롤러
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class CatToyController {

    private final CatToyService catToyService;


    @GetMapping
    public List<CatToy> findAll() {
        return catToyService.findAll();
    }

    @GetMapping("/{id}")
    public CatToy findById(@PathVariable Long id) {
        return catToyService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy create(@RequestBody CatToy catToy) {
        return catToyService.save(catToy);
    }

    @PatchMapping("/{id}")
    public CatToy updateCatToy(@PathVariable Long id, @RequestBody CatToy catToy) {
        return catToyService.updateCatToy(id, catToy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatToy(@PathVariable Long id) {
        catToyService.deleteCatToy(id);
    }



}
