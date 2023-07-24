package com.codesoom.assignment.toy.domain.dto;

import com.codesoom.assignment.toy.domain.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductResponse {
    private final Long id;
    private final String name;
    private final String maker;
    private final Integer price;
    private final String imageUrl;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public static List<ProductResponse> listOf(Iterable<Product> list) {
        ArrayList<ProductResponse> productResponseArrayList = new ArrayList<>();
        for (Product product : list) {
            productResponseArrayList.add(new ProductResponse(product));
        }
        return productResponseArrayList;
    }

}
