package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();

    Category create(Category category);

    Category getCategory(Long id);

    Category update(Long id, Category src);

    void delete(Long id);
}
