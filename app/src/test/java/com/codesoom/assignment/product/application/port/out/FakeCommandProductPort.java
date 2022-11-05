package com.codesoom.assignment.product.application.port.out;

import com.codesoom.assignment.product.domain.Product;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeCommandProductPort implements CommandProductPort {
    private static final AtomicLong productId = new AtomicLong(1L);
    private final List<Product> products;

    public FakeCommandProductPort(List<Product> products) {
        this.products = products;
    }

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

    @Override
    public void delete(Product product) {
        products.remove(product);
    }

    @Override
    public void deleteAllInBatch() {
        products.clear();
    }
}
