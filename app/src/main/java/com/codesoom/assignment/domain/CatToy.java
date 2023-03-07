package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class CatToy {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private int price;

    private String imageUrl;


}
