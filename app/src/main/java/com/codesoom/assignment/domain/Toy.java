package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.Producer;
import com.codesoom.assignment.interfaces.Product;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Toy implements Product {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "PRODUCER_ID")
    private ToyProducer producer;
    private BigDecimal money;


    protected Toy() {
    }

    public Toy(String name, ToyProducer producer, BigDecimal money) {
        this.name = name;
        this.producer = producer;
        this.money = money;
    }

    public Long id() {
        return id;
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
    public BigDecimal price() {
        return money;
    }
}
