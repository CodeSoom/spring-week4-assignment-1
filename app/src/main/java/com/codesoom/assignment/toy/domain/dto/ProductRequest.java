package com.codesoom.assignment.toy.domain.dto;

import com.codesoom.assignment.toy.domain.Product;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProductRequest {
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toProductEntity(){
        return Product.builder()
                .name(this.name)
                .maker(this.maker)
                .price(this.price)
                .imageUrl(this.imageUrl).build();
    }
}
