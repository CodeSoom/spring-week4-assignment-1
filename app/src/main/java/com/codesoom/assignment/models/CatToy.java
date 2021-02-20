package com.codesoom.assignment.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@Getter
public class CatToy implements Toy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String brand;
    Double price;
    String imageURL;

    public CatToy(
            Long id, String name, String brand, Double price, String imageURL
    ) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageURL = imageURL;
    }
}
