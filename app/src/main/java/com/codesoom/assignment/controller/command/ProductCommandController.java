package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.command.ProductCommandService;
import com.codesoom.assignment.domain.ProductCommand.Register;
import com.codesoom.assignment.domain.ProductCommand.UpdateReq;
import com.codesoom.assignment.domain.ProductInfo;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductCommandController {

    private final ProductCommandService productService;
    private final ProductDtoMapper productDtoMapper;

    public ProductCommandController(ProductCommandService productService, ProductDtoMapper productDtoMapper) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfo> list() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfo detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductInfo registerProduct(@RequestBody ProductDto.RequestParam request) {
        final Register command = productDtoMapper.of(request);
        return productService.createProduct(command);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfo updateProduct(@PathVariable Long id, @RequestBody ProductDto.RequestParam request) {
        final UpdateReq command = productDtoMapper.of(id, request);
        return productService.updateProduct(command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
