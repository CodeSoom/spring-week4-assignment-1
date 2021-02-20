package com.codesoom.assignment.domain;

public class Product {
    private Long id;
    private String name;
    private String maker;
    private Integer price;

    public Product(Long id, String name, String maker, Integer price) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
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

    public int getPrice() {
        return price;
    }
}
