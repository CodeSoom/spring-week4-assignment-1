package com.codesoom.assignment.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.codesoom.assignment.model.Product;

@RestController
@CrossOrigin
@RequestMapping("/products")
public final class ProductController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> list() {
        return new ArrayList<>();
    }
}
