package com.codesoom.assignment.controller.query;

import com.codesoom.assignment.application.query.ProductQueryService;
import com.codesoom.assignment.domain.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductQueryController {

    private final ProductQueryService productService;

    public ProductQueryController(ProductQueryService productService) {
        this.productService = productService;
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

}
