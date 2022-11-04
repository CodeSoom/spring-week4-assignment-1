package com.codesoom.assignment.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean hidden;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private final List<Categorization> categorizations = new ArrayList<>();

    public Category() {
    }

    @JsonCreator
    public Category(@JsonProperty("name") String name, @JsonProperty("hidden") Boolean hidden) {
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

    public List<Categorization> getCategorizations() {
        return Collections.unmodifiableList(categorizations);
    }

    public void addCategorization(Categorization categorization) {
        categorizations.add(categorization);
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
