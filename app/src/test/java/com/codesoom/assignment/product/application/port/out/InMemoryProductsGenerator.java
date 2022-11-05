package com.codesoom.assignment.product.application.port.out;

import com.codesoom.assignment.product.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class InMemoryProductsGenerator {
    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }
}
