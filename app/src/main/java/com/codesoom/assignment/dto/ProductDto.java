package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductDto {

    @JsonProperty
    private Long id;
    private final String name;
    private final String maker;
    private final Integer price;
    private final String imageUrl;

    @JsonProperty("category")
    private final List<String> categoryNames;

    @JsonCreator
    public ProductDto(@JsonProperty("name") String name,
                      @JsonProperty("maker") String maker,
                      @JsonProperty("price") Integer price,
                      @JsonProperty("imageUrl") String imageUrl,
                      @JsonProperty("category") List<String> categoryNames
    ) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryNames = categoryNames;
    }

    public ProductDto(Long id, String name, String maker, Integer price, String imageUrl, List<String> categoryNames) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryNames = categoryNames;
    }

    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getMaker(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategoryNames()
        );
    }

    public Product toProduct() {
        return new Product(this.name, this.maker, this.price, this.imageUrl);
    }

    public Long getId() {
        return id;
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

    public List<String> getCategoryNames() {
        return categoryNames;
    }
}
