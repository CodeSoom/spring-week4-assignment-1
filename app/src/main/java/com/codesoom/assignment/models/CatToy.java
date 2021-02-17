package com.codesoom.assignment.models;

public class CatToy implements Toy {
    private final long id;
    private final String name;
    private final String brand;
    private final double price;
    private final String imageURL;

    public CatToy(
            long id,
            String name,
            String brand,
            double price,
            String imageURL
    ) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageURL = imageURL;
    }

    @Override
    public long id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String brand() {
        return this.brand;
    }

    @Override
    public double price() {
        return this.price;
    }

    @Override
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
