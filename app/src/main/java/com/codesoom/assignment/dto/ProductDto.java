package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDto {

    @JsonProperty
    private Long id;
    private final String name;
    private final String maker;
    private final Integer price;
    private final String imageUrl;

    @JsonCreator
    public ProductDto(@JsonProperty("name") String name,
                      @JsonProperty("maker") String maker,
                      @JsonProperty("price") Integer price,
                      @JsonProperty("imageUrl") String imageUrl
    ) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductDto from(Product product) {
        final ProductDto dto = new ProductDto(product.getName(),
                product.getMaker(),
                product.getPrice(),
                product.getImageUrl()
        );
        dto.id = product.getId();

        return dto;
    }

    public Product toProduct() {
        return new Product(this.name, this.maker, this.price, this.imageUrl);
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
