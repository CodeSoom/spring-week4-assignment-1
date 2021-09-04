package com.codesoom.assignment.domain;

import com.google.common.base.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 상품.
 */
@Getter
@DynamicUpdate
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String maker;
    private Long price;
    private String imageUrl;

    /**
     * JPA 에서 생성은 허용하나 직접 생성은 허용하지 않습니다.
     */
    protected Product() {
    }

    @Builder
    private Product(String name, String maker, Long price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    /**
     * 수정될 정보가 담긴 상품을 받아, 현재 상품 정보를 수정하고 리턴합니다.
     *
     * @param source 수정될 정보가 담긴 상품
     * @return 정보가 수정된 현재 상품
     */
    public Product updateInfo(Product source) {
        this.name = source.name;
        this.maker = source.maker;
        this.price = source.price;
        this.imageUrl = source.imageUrl;

        return this;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        return Objects.equal(getName(), product.getName())
            && Objects.equal(getMaker(), product.getMaker())
            && Objects.equal(getPrice(), product.getPrice())
            && Objects.equal(getImageUrl(), product.getImageUrl());
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getMaker(), getPrice(), getImageUrl());
    }

    /**
     * JSON 문자열로 변환하여 리턴합니다.
     *
     * @return 변환된 문자열
     */
    @Override
    public String toString() {
        return String
            .format("{\"name\":\"%s\",\"maker\":\"%s\",\"price\":%s,\"imageUrl\":\"%s\"}", name,
                maker, price, imageUrl);
    }
}
