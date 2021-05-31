package com.codesoom.assignment.models.dto;

import com.codesoom.assignment.models.domain.Product;

/**
 * 상품
 */
public class ProductDto {

    private Long id;

    private String name;

    private String maker;

    private int price;

    private String imageUrl;

    public ProductDto() { }

    public ProductDto(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.maker = p.getMaker();
        this.price = p.getPrice();
        this.imageUrl = p.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
