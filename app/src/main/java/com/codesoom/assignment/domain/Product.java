package com.codesoom.assignment.domain;

import com.codesoom.assignment.exception.CatToyInvalidFieldException;
import com.codesoom.assignment.exception.CatToyInvalidPriceException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Objects;

/**
 * 제품
 */
@DiscriminatorColumn(name = "category")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String maker;
    private Long price;
    private String imageUrl;

    protected Product() {
    }

    protected Product(String name, String maker, Long price, String imageUrl) {
        this(null, name, maker, price, imageUrl);
    }

    protected Product(Long id, String name, String maker, Long price, String imageUrl) {
        if (!updatableStrings(name, maker, imageUrl)) {
            throw new CatToyInvalidFieldException();
        }

        if (!isValidPrice(price)) {
            throw new CatToyInvalidPriceException(price);
        }

        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    protected abstract void update(Product target);

    protected boolean isValidPrice(long price) {
        return price >= 0;
    }

    protected boolean updatableStrings(String... strings) {
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                return false;
            }
        }
        return true;
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
