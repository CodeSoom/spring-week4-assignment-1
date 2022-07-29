package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;
import com.codesoom.assignment.service.ToyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/toys")
public class CatToyController {
    private final ToyService toyService;

    public CatToyController(ToyService toyService) {
        this.toyService = toyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy create(@RequestBody CatToyDto catToyDto) {
        return toyService.create(catToyDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CatToy get(@PathVariable("id") Long id) {
        return toyService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<CatToy> getAll() {
        return toyService.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CatToy update(@PathVariable("id") Long id, @RequestBody CatToyDto dto) {
        return toyService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        toyService.deleteById(id);
    }
}
