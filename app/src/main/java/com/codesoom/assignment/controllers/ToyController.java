package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.services.ToyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<Toy> products() {
        return toyService.getProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Toy create(@RequestBody Toy toy) {
        return toyService.createProduct(toy);
    }

    @GetMapping("{id}")
    public Toy product(@PathVariable Long id) {
        return toyService.getProduct(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        toyService.deleteProduct(id);
    }
}
