package com.codesoom.assignment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Toy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String brand;
    private Integer price;
    private String imageUrl;

    public Toy() {
    }

    public Toy(Long id, String brand, Integer price, String imageUrl) {
        this.id = id;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Toy updatebrand(String brand) {
        return new Toy(this.id, brand, this.price, this.imageUrl);
    }

    public Toy updatePrice(Integer price){
        return new Toy(this.id, this.brand, price, this.imageUrl);
    }

    public Toy updateImageUrl(String imageUrl){
        return new Toy(this.id, this.brand, this.price, imageUrl);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toy toy = (Toy) o;
        return id.equals(toy.id) && brand.equals(toy.brand) && price.equals(toy.price) && imageUrl.equals(toy.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, price, imageUrl);
    }
}
