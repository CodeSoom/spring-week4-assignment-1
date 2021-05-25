package com.codesoom.assignment.controllers;

import com.codesoom.assignment.models.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public List<ProductDto> list() {

        return null;
    }

    @GetMapping("{id}")
    public ProductDto detail(@PathVariable("id") Long productId) {

        return null;
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto) {

        return null;
    }

    @RequestMapping(method={RequestMethod.PUT, RequestMethod.PATCH})
    public ProductDto update(@PathVariable("id") Long productId, @RequestBody ProductDto productDto) {

        return null;
    }

    @DeleteMapping("{id}")
    public ProductDto delete(@PathVariable("id") Long productId) {

        return null;
    }

}
