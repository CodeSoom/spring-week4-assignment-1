package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;

public class ProductRequest {
    private Product product;

    public ProductRequest() {
        product = new Product();
    }
    public void setName(String name) {
        product.setName(name);
    }

    public void setMaker(String maker) {
        product.setMaker(maker);
    }

    public void setPrice(int price) {
        product.setPrice(price);
    }

    public void setImageUrl(String imageUrl) {
        product.setImageUrl(imageUrl);
    }

    public String getName() {
        return product.getName();
    }

    public String getMaker() {
        return product.getMaker();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }

    public Product getProduct() {
        return product;
    }

}
