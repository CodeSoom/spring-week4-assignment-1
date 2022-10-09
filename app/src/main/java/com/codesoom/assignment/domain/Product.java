package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** 상품 **/
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
     * 상품을 수정합니다.
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
}
