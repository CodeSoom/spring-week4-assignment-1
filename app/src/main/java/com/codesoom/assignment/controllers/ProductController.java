package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.utils.ProductDtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto create(@RequestBody ProductDto dto) {
        ProductDtoValidator.validate(dto);

        return productService.create(dto);
    }

    @PatchMapping("/{id}")
    public com.codesoom.assignment.dto.ProductDto update(@PathVariable Long id, @RequestBody ProductDto dto) {
        ProductDtoValidator.checksAllFieldsNull(dto);
        return productService.update(id, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
