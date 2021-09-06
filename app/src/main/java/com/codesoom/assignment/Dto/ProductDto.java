package com.codesoom.assignment.dto;

public final class ProductDto {
    private String name;
    private String maker;
    private String imageUrl;
    private Long price;

    public ProductDto() {
    }

    public ProductDto(
        final String name, final String maker,
        final String imageUrl, final Long price
    ) {
        this.name = name;
        this.maker = maker;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getPrice() {
        return price;
    }
}
