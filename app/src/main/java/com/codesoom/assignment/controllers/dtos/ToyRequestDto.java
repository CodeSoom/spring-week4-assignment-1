package com.codesoom.assignment.controllers.dtos;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

public class ToyRequestDto {
    private final String name;
    private final ToyProducer producer;
    private final BigDecimal price;

    @ConstructorProperties({"name, producer, price"})
    public ToyRequestDto(final String name, final ToyProducer producer, final BigDecimal price) {
        this.name = name;
        this.producer = producer;
        this.price = price;
    }


    public Toy toEntity() {
        return new Toy(name, producer, price);
    }
}
