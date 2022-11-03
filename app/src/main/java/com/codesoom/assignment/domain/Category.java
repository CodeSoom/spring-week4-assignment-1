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

@Entity
public class Category {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean hidden;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private final List<ProductCategory> productCategoryList = new ArrayList<>();

    public Category() {
    }

    public Category(String name, Boolean hidden) {
        this.name = name;
        this.hidden = hidden;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public List<ProductCategory> getProductCategoryList() {
        return Collections.unmodifiableList(productCategoryList);
    }

    public void addProductCategory(ProductCategory productCategory) {
        productCategoryList.add(productCategory);
    }

    public void update(Category src) {
        if (src.getName() != null) {
            this.name = src.getName();
        }

        if (src.getHidden() != null) {
            this.hidden = src.getHidden();
        }
    }
}
