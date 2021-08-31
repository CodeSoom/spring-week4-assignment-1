package com.codesoom.assignment.Dto;

public final class CreateProductDto {
    private final String title;

    public CreateProductDto(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
