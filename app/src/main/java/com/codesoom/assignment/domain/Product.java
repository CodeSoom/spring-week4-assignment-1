package com.codesoom.assignment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String maker;

    @Column
    private Long price;

    @Column
    private String imageUrl;

    public Product() {}

    /**
     * id를 제외한 모든 어트리뷰트를 주어진 상품정보로 갱신합니다.
     *
     * @param product 주어진 상품정보
     */
    public void updateBy(Product product) {
        name = product.getName();
        maker = product.getMaker();
        price = product.getPrice();
        imageUrl = product.getImageUrl();
    }

    /**
     * 상품의 정보를 문자열로 반환합니다.
     *
     * @return 상품 정보
     */
    @Override
    public String toString() {
        return String.format("[Product] id:%d / name:%s / maker:%s / price:%d / imageUrl:%s", id, name, maker, price, imageUrl);
    }

    /**
     * 상품의 id를 반환합니다.
     *
     * @return 상품의 id
     */
    public Long getId() {
        return id;
    }

    /**
     * 상품의 id를 주어진 id로 갱신합니다.
     *
     * @param id 주어진 id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 상품의 name을 반환합니다.
     *
     * @return 상품의 name
     */
    public String getName() {
        return name;
    }

    /**
     * 상품의 name을 주어진 name으로 갱신합니다.
     *
     * @param name 주어진 name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 상품의 maker를 반환합니다.
     *
     * @return 상품의 maker
     */
    public String getMaker() {
        return maker;
    }

    /**
     * 상품의 maker를 주어진 maker로 갱신합니다.
     *
     * @param maker 주어진 maker
     */
    public void setMaker(String maker) {
        this.maker = maker;
    }

    /**
     * 상품의 price를 반환합니다.
     *
     * @return 상품의 price
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 상품의 price를 주어진 price로 갱신합니다.
     *
     * @param price 주어진 price
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * 상품의 imageUrl를 반환합니다.
     *
     * @return 상품의 imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 상품의 imageUrl을 주어진 imageUrl로 갱신합니다.
     *
     * @param imagePath 주어진 imageUrl
     */
    public void setImageUrl(String imagePath) {
        this.imageUrl = imagePath;
    }

    /**
     * 주어진 객체와 동일한지 여부를 반환합니다.
     *
     * @param o 주어진 객체
     * @return 동일한지 여부
     *      타입과 모든 속성 값이 동일하다면 true
     *      동일하지 않다면 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(maker, product.maker) &&
                Objects.equals(price, product.price) &&
                Objects.equals(imageUrl, product.imageUrl);
    }

    /**
     * 객체의 code를 생성하여 반환합니다.
     *
     * @return 객체의 code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imageUrl);
    }
}
