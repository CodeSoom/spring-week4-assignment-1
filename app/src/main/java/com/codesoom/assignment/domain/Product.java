package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


@Getter
@Entity
public class Product {

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

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;

    protected Product() {
    }

    @Builder
    public Product(Long id, String name, String maker, BigDecimal price, String image) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }

    public Product update(ProductDto productDto) {
        this.name = productDto.getName();
        this.maker = productDto.getMaker();
        this.price = productDto.getPrice();
        this.image = productDto.getImage();
        return this;
    }

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

}
