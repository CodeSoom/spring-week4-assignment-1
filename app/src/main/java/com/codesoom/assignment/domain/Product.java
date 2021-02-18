package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 1. Entity (domain)
//  ** RDB의 Entity와 다름
// 2. JPA의 Entity 역할도 같이 함
@Getter
@Setter
@Entity
public class Product {
    // Identifier - Unique Key
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private int price;

    private String image;
}
