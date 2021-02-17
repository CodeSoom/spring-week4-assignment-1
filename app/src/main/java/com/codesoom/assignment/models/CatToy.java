package com.codesoom.assignment.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CatToy implements Toy {
    private final long id;
    private final String name;
    private final String brand;
    private final double price;
    private final String imageURL;

    @JsonCreator
    public CatToy(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("price") double price,
            @JsonProperty("imageURL") String imageURL
    ) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageURL = imageURL;
    }

    @Override
    @JsonGetter
    public long id() {
        return this.id;
    }

    @Override
    @JsonGetter
    public String name() {
        return this.name;
    }

    @Override
    @JsonGetter
    public String brand() {
        return this.brand;
    }

    @Override
    @JsonGetter
    public double price() {
        return this.price;
    }

    @Override
    @JsonGetter
    public String imageURL() {
        return this.imageURL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CatToy) {
            CatToy toy = (CatToy) obj;
            return this.id() == toy.id()
                    && this.name().equals(toy.name())
                    && this.brand().equals(toy.brand())
                    && this.price() == toy.price()
                    && this.imageURL().equals(toy.imageURL());
        }
        return false;
    }
}
