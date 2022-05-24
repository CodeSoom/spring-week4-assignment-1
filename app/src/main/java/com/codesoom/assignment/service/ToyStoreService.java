package com.codesoom.assignment.service;

import com.codesoom.assignment.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ToyStoreService {
    public Product save(String name, String maker, int price, String imageUrl) {
        Product product = new Product();
        product.setId(1L);
        product.setName(name);
        product.setMaker(maker);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        return product;
    }
}
