package com.codesoom.assignment.domain;

/**
 * 장난감 객체입니다.
 */
public class CatToy {

    /**
     * 장난감 식별자
     */
    private Long id;

    /**
     * 장난감 이름
     */
    private String name;

    /**
     * 장난감 메이커
     */
    private String maker;

    /**
     * 장난감 가격
     */
    private int price;

    /**
     * 장난감 이미지
     */
    private String imagePath;


    public CatToy(String name, String maker, int price, String imagePath) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }


}
