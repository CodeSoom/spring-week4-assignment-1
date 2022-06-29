package com.codesoom.assignment.dto;

public class ProductDto {
    private final String name;
    private final String maker;
    private final Long price;
    private final String imageURL;

    public ProductDto(String productName, String makerName, Long productPrice, String imageURL) {
        this.name = productName;
        this.maker = makerName;
        this.price = productPrice;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public Long getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }
}
