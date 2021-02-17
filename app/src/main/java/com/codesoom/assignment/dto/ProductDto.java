package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String name;

    private String maker;

    private Integer price;

    private String imageUrl;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

}
