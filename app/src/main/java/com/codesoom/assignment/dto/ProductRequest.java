package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;

public class ProductRequest {

    private Product product;

    private Product.ProductBuilder productBuilder;

    public ProductRequest() {
        productBuilder = Product.builder();
    }

    public void setName(String name) {
        productBuilder.name(name);
    }

    public void setMaker(String maker) {
        productBuilder.maker(maker);
    }

    public void setPrice(int price) {
        productBuilder.price(price);
    }

    public void setImageUrl(String imageUrl) {
        productBuilder.imageUrl(imageUrl);
    }

    public String getName() {
        return buildProduct().getName();
    }

    public String getMaker() {
        return buildProduct().getMaker();
    }

    public int getPrice() {
        return buildProduct().getPrice();
    }

    public String getImageUrl() {
        return buildProduct().getImageUrl();
    }

    public Product getProduct() {
        return buildProduct();
    }

    private Product buildProduct() {
        if (product != null) {
            return product;
        }

        product = productBuilder.build();

        return product;
    }

}
