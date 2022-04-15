package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductCommandService;
import com.codesoom.assignment.application.ProductQueryService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;



import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;


    public ProductController(ProductCommandService productCommandService, ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductList() {
        List<Product> products = productQueryService.getProducts();
        return products;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable Long id) {
        Product product = productQueryService.getProductById(id);
        return product;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductDto saveRequestDto) {
        Product product = productCommandService.create(saveRequestDto);
        return product;
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody ProductDto updateRequestDto) {
        Product product = productCommandService.updateProduct(id, updateRequestDto);
        return product;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productCommandService.deleteProduct(id);
    }
}
