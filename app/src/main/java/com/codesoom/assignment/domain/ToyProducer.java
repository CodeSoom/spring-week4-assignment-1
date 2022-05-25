package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.Producer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ToyProducer implements Producer {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;
    private String name;

    protected ToyProducer() {
    }

    public ToyProducer(String name) {
        this.name = name;
    }

    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }
}
