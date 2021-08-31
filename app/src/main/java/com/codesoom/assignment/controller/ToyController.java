package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.Toy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ToyController {
    private ToyService toyService;

    public ToyController(ToyService toyService) {
        this.toyService = toyService;
    }

    @GetMapping
    public List<Toy> getAll() {
        return this.toyService.getToys();
    }

    @GetMapping("{id}")
    public Toy get(@PathVariable Long id) {
        return this.toyService.getToy(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Toy create(@RequestBody Toy toy) {
        return this.toyService.saveToy(toy);
    }

    @PutMapping("{id}")
    public Toy put(@PathVariable Long id, @RequestBody Toy toy) {
        return this.toyService.updateToy(id, toy);
    }

    @PatchMapping("{id}")
    public Toy patch(@PathVariable Long id, @RequestBody Toy toy) {
        return this.toyService.updateToy(id, toy);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.toyService.deleteToy(id);
    }
}
