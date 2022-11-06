package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.CategoryDto;

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
public class Category implements Comparable<Category> {

    @Id @GeneratedValue
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean hidden;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private final List<Categorization> categorizations = new ArrayList<>();

    public Category() {
    }

    public Category(String name, Boolean hidden) {
        this.name = name;
        this.hidden = hidden;
    }

    public static Category of(CategoryDto dto) {
        return new Category(dto.getName(), dto.getHidden());
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

    public List<Categorization> getCategorizations() {
        return Collections.unmodifiableList(categorizations);
    }

    public void addCategorization(Categorization categorization) {
        categorizations.add(categorization);
    }

    public void deleteCategorization(Categorization categorization) {
        categorizations.remove(categorization);
    }

    public void update(CategoryDto src) {
        if (src.getName() != null) {
            this.name = src.getName();
        }

        if (src.getHidden() != null) {
            this.hidden = src.getHidden();
        }
    }

    @Override
    public int compareTo(Category c) {
        return this.name.compareTo(c.getName());
    }
}
