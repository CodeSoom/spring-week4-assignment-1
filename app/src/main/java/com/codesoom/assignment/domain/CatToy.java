package com.codesoom.assignment.domain;

import com.codesoom.assignment.exception.CatToyInvalidFieldException;
import com.codesoom.assignment.exception.CatToyInvalidPriceException;
import com.codesoom.assignment.exception.NotSupportedTypeException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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

        if (!updatableStrings(catToyTarget.getName(), catToyTarget.getMaker(), catToyTarget.getImageUrl())) {
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
}
