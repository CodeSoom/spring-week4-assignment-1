package com.codesoom.assignment.domain;

import lombok.Getter;

@Getter
public class CatToyDto {
    private String name;
    private String description;
    private Integer price;
    private String url;

    public CatToyDto() {
    }

    public CatToyDto(String name, String description, Integer price, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }

    public CatToy toCatToy() {
        return new CatToy(name, description, price, url);
    }
}
