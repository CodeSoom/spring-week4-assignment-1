package com.codesoom.assignment.cattoy.domain;

import com.codesoom.assignment.common.domain.Product;
import com.codesoom.assignment.cattoy.exception.CatToyInvalidFieldException;
import com.codesoom.assignment.cattoy.exception.CatToyInvalidPriceException;
import com.codesoom.assignment.cattoy.exception.NotSupportedTypeException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

/**
 * 고양이 장난감
 */
@Entity
@DiscriminatorValue("CatToy")
public class CatToy extends Product {

    public CatToy() {
        super();
    }

    public CatToy(String name, String maker, long price, String imageUrl) {
        super(null, name, maker, price, imageUrl);
    }

    public CatToy(Long id, String name, String maker, long price, String imageUrl) {
        super(id, name, maker, price, imageUrl);
    }

    public static CatToy from(CatToy request) {
        return new CatToy(request.getName(), request.getMaker(), request.getPrice(), request.getImageUrl());
    }

    public static CatToy of(String name, String maker, long price, String imageUrl) {
        return new CatToy(name, maker, price, imageUrl);
    }


    @Override
    public void update(Product target) {
        if (!(target instanceof CatToy)) {
            throw new NotSupportedTypeException(this.getClass().getName(), target.getClass().getName());
        }

        CatToy catToyTarget = (CatToy) target;

        if (!updatableStrings(catToyTarget.getName(), catToyTarget.getMaker())) {
            throw new CatToyInvalidFieldException();
        }

        if (!isValidPrice(catToyTarget.getPrice())) {
            throw new CatToyInvalidPriceException(catToyTarget.getPrice());
        }

        setPrice(catToyTarget.getPrice());
        setName(catToyTarget.getName());
        setMaker(catToyTarget.getMaker());
        setImageUrl(catToyTarget.getImageUrl());
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
        return Objects.equals(this.getId(), catToy.getId())
                && Objects.equals(this.getName(), catToy.getName())
                && Objects.equals(this.getMaker(), catToy.getMaker())
                && Objects.equals(this.getPrice(), catToy.getPrice())
                && Objects.equals(this.getImageUrl(), catToy.getImageUrl());
    }

    @Override
    public int hashCode() {
        final int hashCode = getHashCode();

        if (hashCode != 0) {
            return hashCode;
        }
        setHashCode(Objects.hash(getId(), getName(), getMaker(), getPrice(), getImageUrl()));
        return getHashCode();
    }
}
