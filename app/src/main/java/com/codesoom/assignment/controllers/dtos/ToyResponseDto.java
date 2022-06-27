package com.codesoom.assignment.controllers.dtos;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

public class ToyResponseDto {
    private final Long id;
    private final String name;
    private final ToyProducer producer;
    private final BigDecimal price;

    public ToyResponseDto(final Toy toy) {
        this.id = toy.id();
        this.name = toy.name();
        this.producer = toy.producer();
        this.price = toy.price();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ToyProducer getProducer() {
        return producer;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
