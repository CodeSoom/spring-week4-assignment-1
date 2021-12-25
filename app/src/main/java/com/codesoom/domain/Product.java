package com.codesoom.domain;

import org.checkerframework.checker.signature.qual.Identifier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 상품 정보를 저장하는 객체
 */
@Entity
public class Product {
    /**
     * 상품 아이디
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 상품 이름
     */
    private String name;
    /**
     * 상품 메이커
     */
    private String maker;
    /**
     * 상품의 가격 (단위:원)
     */
    private BigDecimal price;
    /**
     * 상품 이미지 URL
     */
    private String imageUrl;

    /**
     * 상품의 id를 반환한다.
     *
     * @return 상품의 id
     */
    public Long getId() {
        return id;
    }

    /**
     * id를 상품 객체에 담는다.
     *
     * @param id 상품 id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 상품의 이름을 반환한다.
     *
     * @return 상품의 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 이름을 상품 객체에 담는다.
     *
     * @param name 상품 이름
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 상품의 메이커를 반환한다.
     *
     * @return 상품의 메이커
     */
    public String getMaker() {
        return maker;
    }

    /**
     * 메이커를 상품 객체에 담는다.
     *
     * @param maker 상품 메이커
     */
    public void setMaker(String maker) {
        this.maker = maker;
    }

    /**
     * 상품의 가격을 리턴한다.
     *
     * @return 상품의 가격
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 가격을 받아 상품 객체에 담는다.
     *
     * @param price 상품 가격 (단위: 원)
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 상품의 이미지 URL을 반환한다.
     *
     * @return imageUrl 상품 이미지 URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 이미지 url을 받아 담는다.
     *
     * @param imageUrl 상품 이미지
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maker='" + maker + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
