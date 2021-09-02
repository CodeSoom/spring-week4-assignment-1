package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Product 리소스
 */
@Entity
public final class Product {
    @Id
    @GeneratedValue
    private Long id;

    private final String title;

    public Product(final String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}