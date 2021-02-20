package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.Toy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<Toy> list() {
        return toyService.getToys();
    }

    @GetMapping("{id}")
    public Toy detail(@PathVariable Long id) {
        return toyService.getToy(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Toy create(@RequestBody Toy toy) {
        return toyService.createToy(toy);
    }

    @PatchMapping("{id}")
    public Toy patch(@PathVariable Long id, @RequestBody Toy toy) {
        return null;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

    }
}
