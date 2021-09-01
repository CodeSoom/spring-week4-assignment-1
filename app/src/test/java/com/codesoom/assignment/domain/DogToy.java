package com.codesoom.assignment.domain;

import java.util.Objects;


public class DogToy extends Product{

    public DogToy() {
        super();
    }

    public DogToy(String name, String maker, Long price, String imageUrl) {
        super(null, name, maker, price, imageUrl);
    }

    public DogToy(Long id, String name, String maker, Long price, String imageUrl) {
        super(id, name, maker, price, imageUrl);
    }

    @Override
    protected void update(Product target) {}

    public static DogToy of(String name, String maker, Long price, String imageUrl) {
        return new DogToy(name, maker, price, imageUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DogToy)) {
            return false;
        }

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
