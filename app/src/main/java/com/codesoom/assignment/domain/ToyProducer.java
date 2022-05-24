package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.Producer;

public class ToyProducer implements Producer {
    private final String name;

    public ToyProducer(String name){
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
