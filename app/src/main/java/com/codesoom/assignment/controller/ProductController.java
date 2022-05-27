package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/products")
@RestController
@CrossOrigin
public class ProductController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> list() {
        return new ArrayList<ProductDto>();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto detail(@PathVariable Long id) {
        return new ProductDto();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto register(@RequestBody ProductDto productDto) {
        return productDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto modify(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {

    }
}
