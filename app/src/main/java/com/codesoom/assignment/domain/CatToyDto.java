package com.codesoom.assignment.domain;

import lombok.Getter;

@Getter
public class CatToyDto {
    private String name;
    private String maker;
    private Integer price;
    private String url;

    public CatToyDto() {
    }

    public CatToyDto(String name, String maker, Integer price, String url) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.url = url;
    }

    public CatToy toCatToy() {
        return new CatToy(name, maker, price, url);
    }
}
