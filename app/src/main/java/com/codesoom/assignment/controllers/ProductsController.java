package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.CatToyDTO;
import com.codesoom.assignment.exceptions.ToyNotFoundException;
import com.codesoom.assignment.models.Toy;
import com.codesoom.assignment.services.ToyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductsController {
    private final ToyService toyService;

    public ProductsController(ToyService toyService) {
        this.toyService = toyService;
    }

    @GetMapping
    public List<CatToyDTO> getAllProducts() {
        List<Toy> toys = toyService.find();

        return toys.stream().map(CatToyDTO::new).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public Toy getProduct(@PathVariable Long id) throws ToyNotFoundException {
        return toyService.find(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Toy toy) {
        toyService.insert(toy);
    }

    @PatchMapping("{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Toy toy) throws ToyNotFoundException {
        toyService.modify(id, toy);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) throws ToyNotFoundException {
        toyService.delete(id);
    }
}
