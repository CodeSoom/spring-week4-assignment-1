package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ToyRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private Long newId = 0L;

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
//        return products.stream()
//                .filter(product -> product.getId().equals(id))
//                .findFirst();

        return null;
    }

    @Override
    public Product save(Product source) {
//        Product product = new Product();
//        product.setId(generateId());
//        product.setName(source.getName());
//        product.setMaker(source.getMaker());
//        product.setPrice(source.getPrice());
//        product.setImageURI(source.getImageURI());
//
//        products.add(product);
//
//        return product;
        return null;
    }

//    @Override
//    public Product update(Long id, Product product) {
//        return null;
//    }
//
//    @Override
//    public Product delete(Product product) {
//        return null;
//    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
