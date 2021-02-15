package com.codesoom.assignment.product.ui;

import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.domain.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    final ProductService productService;

    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }
}
