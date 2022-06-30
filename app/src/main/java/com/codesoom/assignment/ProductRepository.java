package com.codesoom.assignment;

import com.codesoom.assignment.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ProductRepository {
    Map<Long, Product> products = new ConcurrentHashMap();

    public Optional<Product> findById(long id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product save(Product product) {
        Product productEntity = new Product(IdGenerator.next(), product.getName(), product.getMaker(), product.getPrice(), product.getImageUrl());
        products.put(productEntity.getId(), productEntity);

        return productEntity;
    }

    public Product update(Product product) {
        products.put(product.getId(), product);

        return product;
    }

    public List<Product> findAll() {
        return new ArrayList<>(this.products.values()).stream()
                .sorted((t1, t2) -> t1.getId().compareTo(t2.getId()))
                .collect(Collectors.toList());
    }

    public Optional<Product> deleteById(Long id) {
        return Optional.ofNullable(products.remove(id));
    }
}
