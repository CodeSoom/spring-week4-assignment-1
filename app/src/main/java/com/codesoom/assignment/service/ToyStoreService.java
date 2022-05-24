package com.codesoom.assignment.service;

import com.codesoom.assignment.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ToyStoreService {
    public Product save(Product product) {
        product.setId(1L);
        return product;
    }
}
