package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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

    public Toy(Long id, String name, ToyProducer producer, BigDecimal money) {
        this.id = id;
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
    public ToyProducer producer() {
        return producer;
    }

    @Override
    public BigDecimal price() {
        return money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Toy toy = (Toy) o;

        return Objects.equals(id, toy.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
