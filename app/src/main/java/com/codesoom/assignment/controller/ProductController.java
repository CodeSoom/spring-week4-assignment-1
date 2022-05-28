package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> list() {
        return productService.getProducts();
    }

}
