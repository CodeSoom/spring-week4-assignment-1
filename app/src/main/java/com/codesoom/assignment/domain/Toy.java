package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


/**
 * 장난감 엔티티
 */
@Getter
@Entity
public class Toy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String maker;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String image;

    protected Toy() {
    }

    @Builder
    public Toy(Long id, String name, String maker, BigDecimal price, String image) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    public Toy update(ToyDto toyDto) {
        this.name = toyDto.getName();
        this.maker = toyDto.getMaker();
        this.price = toyDto.getPrice();
        this.image = toyDto.getImage();
        return this;
    }

}
