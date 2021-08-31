package com.codesoom.assignment.domain;

import com.codesoom.assignment.exception.CatToyInvalidFieldException;
import com.codesoom.assignment.exception.CatToyInvalidPriceException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * 고양이 장난감
 */
@Entity
public class CatToy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String maker;
    private Long price;
    private String imageUrl;

    public CatToy() {
    }

    public CatToy(String name, String maker, Long price, String imageUrl) {
        this(null, name, maker, price, imageUrl);
    }

    public CatToy(Long id, String name, String maker, Long price, String imageUrl) {
        if (!isValidPrice(price)) {
            throw new CatToyInvalidPriceException(price);
        }

        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private boolean isValidPrice(Long price) {
        return price >= 0;
    }

    public CatToy(Builder builder) {
        name = builder.name;
        maker = builder.maker;
        price = builder.price;
        imageUrl = builder.imageUrl;
    }

    public static CatToy from(CatToy request) {
        return new CatToy(request.name, request.maker, request.price, request.imageUrl);
    }

    public static CatToy of(String name, String maker, Long price, String imageUrl) {
        return new CatToy(name, maker, price, imageUrl);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void update(CatToy target) {
        if (!updatableStrings(target.name, target.maker, target.imageUrl)) {
            throw new CatToyInvalidFieldException();
        }

        if (!isValidPrice(target.price)) {
            throw new CatToyInvalidPriceException(target.price);
        }

        price = target.price;
        name = target.name;
        maker = target.maker;
        imageUrl = target.imageUrl;
    }

    private boolean updatableStrings(String... strings) {
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static class Builder {
        private String name;
        private String maker;
        private Long price;
        private String imageUrl;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder maker(String maker) {
            this.maker = maker;
            return this;
        }

        public Builder price(Long price) {
            this.price = price;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public CatToy build() {
            return new CatToy(this);
        }
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

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatToy)) {
            return false;
        }
        CatToy catToy = (CatToy) o;
        return Objects.equals(id, catToy.id)
                && Objects.equals(name, catToy.name)
                && Objects.equals(maker, catToy.maker)
                && Objects.equals(price, catToy.price)
                && Objects.equals(imageUrl, catToy.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imageUrl);
    }
}
