package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.Money;
import com.codesoom.assignment.interfaces.Producer;
import com.codesoom.assignment.interfaces.Product;

public class Toy implements Product {
    private final String name;
    private final Producer producer;
    private final Money money;

    public Toy(String name, Producer producer, Money money) {
        this.name = name;
        this.producer = producer;
        this.money = money;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Producer producer() {
        return producer;
    }

    @Override
    public Money price() {
        return money;
    }
}
