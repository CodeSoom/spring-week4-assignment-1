package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/** 장난감 **/
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    /** 이름 **/
    private String name;

    /** 가게 **/
    private String maker;

    /** 가격 **/
    private int price;

    /** 이미지 **/
    private String imageUrl;

    public Product() {
    }

    public Product(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    /**
     * 장난감을 수정합니다.
     *
     * @param name 수정할 이름
     * @param maker 수정할 가게
     * @param price 수정할 가격
     * @param imageUrl 수정할 이미지
     */
    public void update(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return price == product.price
                && Objects.equals(id, product.id)
                && Objects.equals(name, product.name)
                && Objects.equals(maker, product.maker)
                && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imageUrl);
    }
}
