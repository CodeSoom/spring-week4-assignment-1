package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.Shop;

public class ToyShop implements Shop {
    private final String name;

    public ToyShop(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
