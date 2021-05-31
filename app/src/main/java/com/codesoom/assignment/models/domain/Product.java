package com.codesoom.assignment.models.domain;

import com.codesoom.assignment.models.dto.ProductInfoDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 상품
 */
@Entity
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;

    private String maker;

    private int price;

    private String imgUrl;

    public Product() {}

    public Product(String name, String maker, int price, String imgUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Product(Long id, String name, String maker, int price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 자신(상품)의 정보를 수정합니다.
     * @param updateParam 수정할 정보
     * @return 수정된 상품을 반환합니다.
     */
    public Product update(ProductInfoDto updateParam) {

        this.name = updateParam.getName();
        this.maker = updateParam.getMaker();
        this.price = updateParam.getPrice();
        this.imgUrl = updateParam.getImageUrl();

        return this;
    }
}
