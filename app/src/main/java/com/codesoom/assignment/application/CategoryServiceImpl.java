package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Category;
import com.codesoom.assignment.domain.CategoryRepository;
import com.codesoom.assignment.dto.CategoryDto;
import com.codesoom.assignment.exceptions.CategoryNotFoundException;
import com.codesoom.assignment.exceptions.DuplicateCategoryException;
import com.codesoom.assignment.exceptions.InvalidDeleteRequestException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::of)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        return CategoryDto.of(category);
    }

    @Override
    public CategoryDto create(CategoryDto dto) {
        if (categoryRepository.isCategoryExisting(dto.getName())) {
            throw new DuplicateCategoryException(dto.getName());
        }

        final Category savedCategory = categoryRepository.save(Category.of(dto));
        return CategoryDto.of(savedCategory);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto src) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (categoryRepository.isCategoryExisting(src.getName())) {
            throw new DuplicateCategoryException(src.getName());
        }

        category.update(src);
        return CategoryDto.of(category);
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
