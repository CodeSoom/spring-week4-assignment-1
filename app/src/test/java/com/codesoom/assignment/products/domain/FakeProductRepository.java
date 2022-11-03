package com.codesoom.assignment.products.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Primary
public class FakeProductRepository implements ProductRepository {
    private List<Product> products = new ArrayList<>();
    private static final AtomicLong productId = new AtomicLong(1L);

    public Product save(Product entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }

        Product product = Product.builder()
                .id(productId.getAndIncrement())
                .name(entity.getName())
                .maker(entity.getMaker())
                .price(entity.getPrice())
                .imgUrl(entity.getImgUrl())
                .build();

        products.add(product);

        return product;
    }

    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(Product product) {
    }

    @Override
    public void deleteAllInBatch() {
        products = new ArrayList<>();
    }
}
