package com.codesoom.assignment.models.dto;

import com.codesoom.assignment.models.domain.Product;

/**
 * 상품 정보
 */
public class ProductInfoDto {

    private String name;

    private String maker;

    private int price;

    private String imageUrl;

    public ProductInfoDto() { }

    public ProductInfoDto(Product p) {
        this.name = p.getName();
        this.maker = p.getMaker();
        this.price = p.getPrice();
        this.imageUrl = p.getImgUrl();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
