package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final Map<Long, Product> products = new LinkedHashMap<>();
    private Long product_Id = 0L;

    @GetMapping
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
