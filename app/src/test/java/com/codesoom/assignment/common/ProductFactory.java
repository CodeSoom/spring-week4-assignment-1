package com.codesoom.assignment.common;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductCommand;

import java.util.UUID;

public class ProductFactory {
    public static Product createProduct() {
        return Product.builder()
                .name("고양이 장난감")
                .maker("제조사")
                .price(randomPrice())
                .imageUrl(UUID.randomUUID().toString() + ".png")
                .build();
    }

    public static Product createProduct(Long id) {
        Product.ProductBuilder product = Product.builder();

        System.out.println(product.toString());

        product.id(id)
                .name("고양이 장난감" + id)
                .maker("제조사" + id)
                .price(randomPrice())
                .imageUrl(UUID.randomUUID().toString() + ".png")
                .build();

        return product.build();
    }

    public static ProductCommand.Register of(Product product) {
        ProductCommand.Register.RegisterBuilder register = ProductCommand.Register.builder();

//            register.id(product.getId());
        register.name(product.getName());
        register.maker(product.getMaker());
        register.price(product.getPrice());
        register.imageUrl(product.getImageUrl());

        return register.build();
    }

    public static Long randomPrice() {
        return (long) (Math.ceil((Math.random() * 10000 + 10000) / 1000) * 1000);
    }
}
