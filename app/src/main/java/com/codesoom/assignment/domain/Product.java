package com.codesoom.assignment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Product {

    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private final List<Categorization> categorizations = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getCategoryNames() {
        return categorizations.stream()
                .map(categorization -> categorization.getCategory().getName())
                .collect(Collectors.toUnmodifiableList());
    }

    public void addCategories(List<Category> categories) {
        for (Category category : categories) {
            addCategorization(new Categorization(this, category));
        }
    }

    public void addCategorization(Categorization categorization) {
        categorizations.add(categorization);
        categorization.getCategory().addCategorization(categorization);
    }

    private void removeAllCategories() {
        for (Categorization categorization : categorizations) {
            categorization.getCategory()
                    .getCategorizations()
                    .remove(categorization);
        }

        categorizations.clear();
    }

    public void updateCategories(List<Category> categories) {
        removeAllCategories();
        addCategories(categories);
    }

    public void updateInfo(String name, String maker, Integer price, String imageUrl, List<Category> categories) {
        if (name != null) {
            this.name = name;
        }

        if (maker != null) {
            this.maker = maker;
        }

        if (price != null) {
            this.price = price;
        }

        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }

        if (categories != null) {
            updateCategories(categories);
        }
    }

}
