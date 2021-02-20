package com.codesoom.assignment.models;

public class Product {


    private String name;

    private Long id;

    private String maker;

    private Integer price;

    private String imageUrl;


    public Product() {};

    public Product(String name, String maker, Integer price) {
        this.name = name;
        this.maker = maker;
        this.price = price;
    };

    public Product(Long id, String name, String maker, Integer price) {
        this.name = name;
        this.id = id;
        this.maker = maker;
        this.price = price;
    }

//    public ProductWithoutId(String name, String maker, Integer price, String imageUrl) {
//        this.name = name;
//        this.id = id;
//        this.maker = maker;
//        this.price = price;
//        this.imageUrl = imageUrl;
//    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

}
