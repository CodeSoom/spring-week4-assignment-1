package com.codesoom.assignment.product.domain;

import com.codesoom.assignment.product.exception.ProductInvalidFieldException;
import com.codesoom.assignment.product.exception.ProductInvalidPriceException;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * 상품
 */
@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String maker;
    private Long price;
    private String imageUrl;

    public Product() {
    }

    public Product(String name, String maker, long price, String imageUrl) {
        this(null, name, maker, price, imageUrl);
    }

    public Product(Long id, String name, String maker, Long price, String imageUrl) {
        if (isNotUpdatableString(name)) {
            throw new ProductInvalidFieldException();
        }

        if (isNotUpdatableString(maker)) {
            throw new ProductInvalidFieldException();
        }

        if (!isValidPrice(price)) {
            throw new ProductInvalidPriceException(price);
        }

        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static Product from(Product request) {
        return new Product(request.name, request.maker, request.price, request.imageUrl);
    }

    public static Product of(String name, String maker, long price, String imageUrl) {
        return new Product(name, maker, price, imageUrl);
    }


    public void update(Product target) {

        if (isNotUpdatableString(target.name)) {
            throw new ProductInvalidFieldException();
        }

        if (isNotUpdatableString(target.maker)) {
            throw new ProductInvalidFieldException();
        }

        if (!isValidPrice(target.price)) {
            throw new ProductInvalidPriceException(target.price);
        }

        this.id = target.id;
        this.name = target.name;
        this.maker = target.maker;
        this.price = target.price;
        this.imageUrl = target.imageUrl;
    }

    private boolean isValidPrice(long price) {
        return price > 0;
    }

    private boolean isNotUpdatableString(String value) {
        return value == null || value.isBlank();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;

        return Objects.equals(id, product.id)
                && Objects.equals(name, product.name)
                && Objects.equals(maker, product.maker)
                && Objects.equals(price, product.price)
                && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imageUrl);
    }
}
