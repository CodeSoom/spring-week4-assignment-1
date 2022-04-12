package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Domain 에서의 Entity와 RDB 에서의 Entity는 다름
 * 여기서는 두개의 역활을 하는 Entity로 사용
 */
@Getter
@Entity
public class Product {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Setter
    @Column
    public String name;

    @Setter
    @Column
    public String maker;

    @Setter
    @Column
    public Integer price;

    @Setter
    @Column
    public String imageUrl;

    @Override
    public String toString() {
        return String.format(
                "{ id = %s name = %s, maker = %s, price = %s, imageUrl = %s}",
                id,
                name,
                maker,
                price,
                imageUrl
        );
    }
}