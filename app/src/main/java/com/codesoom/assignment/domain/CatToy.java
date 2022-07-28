package com.codesoom.assignment.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 고양이 장난감 정보를 담는 역할을 합니다.
 */
@Entity
public class CatToy {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private int price;

    private String imageURL;

    /**
     * 고양이 장난감 정보를 생성합니다.
     * @param name 장난감 이름
     * @param maker 장난감 메이커
     * @param price 장난감 가격
     */
    @JsonCreator
    public CatToy(@JsonProperty(value = "name", required = true) String name,
                  @JsonProperty(value = "maker", required = true) String maker,
                  @JsonProperty(value = "price", required = true) int price,
                  @JsonProperty(value = "imageUrl") String imageURL) {
        this(null, name, maker, price, imageURL);
    }

    /**
     * 고양이 장난감 정보를 지정된 Id로 생성합니다.
     * @param id 식별자
     * @param name 장난감 이름
     * @param maker 장난감 메이커
     * @param price 장난감 가격
     */
    public CatToy(Long id, String name, String maker, int price, String imageURL) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageURL = imageURL;
    }

    public CatToy() { }

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
