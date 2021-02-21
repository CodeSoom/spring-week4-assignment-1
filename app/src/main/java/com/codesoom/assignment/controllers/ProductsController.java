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
    public CatToyDTO getProduct(@PathVariable Long id) throws ToyNotFoundException {
        Toy toy = toyService.find(id);

        return new CatToyDTO(toy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody CatToyDTO toyDTO) {
        Toy toy = toyDTO.toEntity();

        toyService.insert(toy);
    }

    @PatchMapping("{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody CatToyDTO toyDTO) throws ToyNotFoundException {
        Toy toy = toyDTO.toEntity();

        toyService.modify(id, toy);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) throws ToyNotFoundException {
        toyService.delete(id);
    }
}
