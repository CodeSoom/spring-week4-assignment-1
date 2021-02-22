package com.codesoom.assignment.models;

public interface Toy {
    Long id();

    String name();

    String brand();

    Double price();

    String imageURL();

    void modify(
            String name, String brand, Double price, String imageURL
    );

    void modify(Toy toy);
}
