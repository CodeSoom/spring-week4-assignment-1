package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.utils.ProductDtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<com.codesoom.assignment.dto.ProductDto> getProducts() {
        return service.getProducts().stream()
                .map(com.codesoom.assignment.dto.ProductDto::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public com.codesoom.assignment.dto.ProductDto getProduct(@PathVariable Long id) {
        final Product product = service.getProduct(id);
        return com.codesoom.assignment.dto.ProductDto.from(product);
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public ProductDto create(@RequestBody ProductDto dto) {
//        ProductDtoValidator.validate(dto);
//
//        final Product product = service.create(dto.toProduct());
//        return ProductDto.from(product);
//    }

    @PatchMapping("/{id}")
    public com.codesoom.assignment.dto.ProductDto update(@PathVariable Long id, @RequestBody com.codesoom.assignment.dto.ProductDto dto) {
        ProductDtoValidator.checksAllFieldsNull(dto);

        final Product product = service.update(id, dto.toProduct());
        return com.codesoom.assignment.dto.ProductDto.from(product);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
