package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;

import java.util.Objects;

public class ProductDto {
    private String name;

    private String maker;

    private Integer price;

    private String img;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.img = product.getImg();
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductDto that = (ProductDto) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getMaker(), that.getMaker()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getImg(), that.getImg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getMaker(), getPrice(), getImg());
    }
}
