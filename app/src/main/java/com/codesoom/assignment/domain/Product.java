package com.codesoom.assignment.domain;

import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private Integer price;

    private String imagePath;

    public Product() {
    }

    public Product(String name, String maker, Integer price, String imagePath) {
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

    public Integer getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    /**
     * 상품 정보를 대체합니다.
     * @param updateRequest 대체될 상품 데이터
     */
    public void replace(ProductUpdateRequest updateRequest) {
        name = updateRequest.getName();
        maker = updateRequest.getMaker();
        price = updateRequest.getPrice();
        imagePath = updateRequest.getImageUrl();
    }

    /**
     * 상품 정보를 변경합니다.
     * @param updateRequest 변경될 상품 데이터
     */
    public void update(ProductUpdateRequest updateRequest) {
        if (StringUtils.hasText(updateRequest.getName())) {
            name = updateRequest.getName();
        }

        if (StringUtils.hasText(updateRequest.getMaker())) {
            maker = updateRequest.getMaker();
        }

        if (updateRequest.getPrice() != null && updateRequest.getPrice() > 0) {
            price = updateRequest.getPrice();
        }

        if (StringUtils.hasText(updateRequest.getImageUrl())) {
            imagePath = updateRequest.getImageUrl();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "CatToy{id=%s, name=%s, maker=%s, price=%d, imagePath=%s}", id, name, maker, price, imagePath);
    }
}
