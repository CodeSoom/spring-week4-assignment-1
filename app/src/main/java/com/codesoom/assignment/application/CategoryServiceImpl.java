package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Category;
import com.codesoom.assignment.domain.CategoryRepository;
import com.codesoom.assignment.exceptions.CategoryNotFoundException;
import com.codesoom.assignment.exceptions.DuplicateCategoryException;
import com.codesoom.assignment.exceptions.InvalidDeleteRequestException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(Category category) {
        if (categoryRepository.existsCategory(category.getName())) {
            throw new DuplicateCategoryException(category.getName());
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category update(Long id, Category src) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (categoryRepository.existsCategory(src.getName())) {
            throw new DuplicateCategoryException(src.getName());
        }

        category.update(src);
        return category;
    }

    @Override
    public void delete(Long id) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (!category.getCategorizations().isEmpty()) {
            throw new InvalidDeleteRequestException();
        }

        categoryRepository.delete(category);
    }
}
