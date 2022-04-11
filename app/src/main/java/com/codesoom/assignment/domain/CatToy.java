package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CatToy {

    @Id
    @GeneratedValue
    private Long id;

    private String maker;

    private Integer price;

    private String imagePath;

    public CatToy() {
    }

    public CatToy(Long id) {
        this.id = id;
    }
}
