package com.codesoom.assignment.domain;

import java.util.Objects;


public class DogToy {

    private Long id;

    private String name;
    private String maker;
    private Long price;
    private String imageUrl;

    public DogToy() {
    }

    public DogToy(String name, String maker, Long price, String imageUrl) {
        this(null, name, maker, price, imageUrl);
    }

    public DogToy(Long id, String name, String maker, Long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static DogToy of(String name, String maker, Long price, String imageUrl) {
        return new DogToy(name, maker, price, imageUrl);
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
        if (this == o){ return true;}

        if (!(o instanceof DogToy)){ return false;}

        DogToy dogToy = (DogToy) o;

        return Objects.equals(id, dogToy.id)
                && Objects.equals(name, dogToy.name)
                && Objects.equals(maker, dogToy.maker)
                && Objects.equals(price, dogToy.price)
                && Objects.equals(imageUrl, dogToy.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imageUrl);
    }
}
