package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories();

    CategoryDto create(CategoryDto category);

    CategoryDto getCategory(Long id);

    CategoryDto update(Long id, CategoryDto src);

    void delete(Long id);
}
