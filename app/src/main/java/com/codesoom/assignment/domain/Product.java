package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public final class Product {
    @Id
    @GeneratedValue
    private Long id;

    private final String title;

    public Product(final Long idOrNull, final String title) {
        this.id = idOrNull;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}