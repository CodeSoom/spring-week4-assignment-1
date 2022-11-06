package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Category;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDto {

    private Long id;
    private final String name;
    private final Boolean hidden;

    public CategoryDto(Long id, String name, Boolean hidden) {
        this.id = id;
        this.name = name;
        this.hidden = hidden;
    }

    @JsonCreator
    public CategoryDto(@JsonProperty("name") String name, @JsonProperty("hidden") Boolean hidden) {
        this.name = name;
        this.hidden = hidden;
    }

    public static CategoryDto of(Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getHidden());
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
}
