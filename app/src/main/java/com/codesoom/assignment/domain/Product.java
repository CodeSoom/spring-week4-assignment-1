package com.codesoom.assignment.domain;

/**
 * 고양이 장난감 쇼핑몰
 * Product 모델
 * user 모델
 * Order 모델
 * ... 모델
 * Application (UseCase)
 * Product -> 관리자 등록/수정/삭제 -> list/detail
 * 주문 -> 확인 -> 배송 등 처리
 * <p>
 * <p>
 * Product
 * 0. 식별자 - identifier (ID)
 * 1. 이름 - 쥐돌이
 * 2. 제조사 - 냥이 월드
 * 3. 가격 - 5,000원 (판매가)
 * 4. 이미지 - static, CDN => image URL
 */

public class Product {

    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;


    public Product(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, String maker, int price) {
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public Product(long id, String name, String maker, int price) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public Product() {
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

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
