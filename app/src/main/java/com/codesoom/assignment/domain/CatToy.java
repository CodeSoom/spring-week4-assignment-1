package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CatToy {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private int price;

    private String imgUrl;
}
