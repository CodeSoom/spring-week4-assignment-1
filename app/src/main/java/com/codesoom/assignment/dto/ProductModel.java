package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductModel {
    private Long id;
    private final String name;
    private final String maker;
    private final Integer price;
    private final String imageUrl;

    public ProductModel(Product catToy) {
        this (catToy.id(), catToy.name(), catToy.maker(), catToy.price(), catToy.imageUrl());
    }

    public ProductModel(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductModel(Long id, String name, String maker, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static List<ProductModel> ofList(List<Product> catToys) {
        return catToys.stream()
                .map(ProductModel::new)
                .collect(Collectors.toList());
    }


    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String maker() {
        return maker;
    }

    public Integer price() {
        return price;
    }

    public String imageUrl() {
        return imageUrl;
    }
}
