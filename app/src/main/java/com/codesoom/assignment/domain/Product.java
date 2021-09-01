package com.codesoom.assignment.domain;

import com.google.common.base.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 상품.
 */
@Entity
@DynamicUpdate
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String maker;
    private long price;
    private String imageUrl;

    public Product() {

    }

    public Product(String name, String maker, long price, String imageUrl) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        return price == product.price
            && Objects.equal(id, product.id)
            && Objects.equal(name, product.name)
            && Objects.equal(maker, product.maker)
            && Objects.equal(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, maker, price, imageUrl);
    }
}
