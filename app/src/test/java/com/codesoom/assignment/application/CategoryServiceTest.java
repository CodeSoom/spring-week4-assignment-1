package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.CategoryDto;
import com.codesoom.assignment.exceptions.CategoryNotFoundException;
import com.codesoom.assignment.exceptions.DuplicateCategoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DisplayName("CategoryService 클래스의")
public class CategoryServiceTest {

    private static final String CATEGORY_NAME = "category name";
    private static final Boolean HIDDEN = true;

    private static final String UPDATED_CATEGORY_NAME = "updated category name";
    private static final Boolean VISIBLE = false;

    @Autowired
    private CategoryService categoryService;

    private Long getExistingId() {
        return categoryService.getCategories()
                .get(0)
                .getId();
    }

    @Test
    @DisplayName("getCategories()는 등록한 카테고리가 없으면 빈 리스트를 리턴한다.")
    void given_none_categories_registered_when_getCategories_invoked_then_returns_empty_list() {
        List<CategoryDto> list = categoryService.getCategories();

        assertThat(list).isEmpty();
    }

    @Test
    @DisplayName("getCategories()는 등록한 카테고리가 있으면 비어 있지 않은 리스트를 리턴한다.")
    void given_categories_registered_exists_when_getCategories_invoked_then_returns_not_empty_list() {
        categoryService.create(new CategoryDto(CATEGORY_NAME, HIDDEN));

        List<CategoryDto> list = categoryService.getCategories();

        assertThat(list).isNotEmpty();
    }

    @Test
    @DisplayName("getCategory()는 등록된 카테고리의 id를 인자로 호출하면 해당 카테고리를 리턴한다.")
    void when_getCategory_invoked_with_registered_id_then_corresponding_category_returned() {
        categoryService.create(new CategoryDto(CATEGORY_NAME, HIDDEN));

        Long id = getExistingId();
        CategoryDto category = categoryService.getCategory(id);

        assertThat(category.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("getCategory()는 등록되지 않은 카테고리의 id를 인자로 호출하면 예외를 던진다.")
    void when_getCategory_invoked_with_not_registered_id_then_exception_thrown() {
        categoryService.create(new CategoryDto(CATEGORY_NAME, HIDDEN));

        assertThatThrownBy(() -> categoryService.getCategory(Long.MAX_VALUE))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("create()는 인자로 주어진 category 객체를 저장한다.")
    void when_create_invoked_then_that_category_is_saved_in_repository() {
        int oldSize = categoryService.getCategories().size();

        categoryService.create(new CategoryDto(CATEGORY_NAME, HIDDEN));

        int newSize = categoryService.getCategories().size();
        assertThat(newSize - oldSize).isEqualTo(1);
    }

    @Test
    @DisplayName("create()는 고유 id가 부여된 category 객체를 할당한다.")
    void when_create_invoked_then_returns_category_with_unique_id() {
        Set<Long> idSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            CategoryDto category = categoryService.create(new CategoryDto(CATEGORY_NAME + i, HIDDEN));
            idSet.add(category.getId());
        }

        assertThat(idSet.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("create()는 이미 존재하는 category 이름을 인자로 호출하면 예외를 던진다.")
    void when_create_invoked_with_category_name_already_existing_then_throws_exception() {
        categoryService.create(new CategoryDto(CATEGORY_NAME, HIDDEN));

        assertThatThrownBy(() -> categoryService.create(new CategoryDto(CATEGORY_NAME, HIDDEN)))
                .isInstanceOf(DuplicateCategoryException.class);
    }

    @Test
    @DisplayName("update()는 존재하는 id를 인자로 호출하면 해당 category 객체의 정보를 수정한다.")
    void when_update_invoked_with_existing_id_then_corresponding_category_is_updated() {
        categoryService.create(new CategoryDto(CATEGORY_NAME, HIDDEN));

        Long id = getExistingId();
        CategoryDto src = new CategoryDto(UPDATED_CATEGORY_NAME, VISIBLE);
        CategoryDto category = categoryService.update(id, src);

        assertThat(category.getName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(category.getHidden()).isEqualTo(VISIBLE);
    }

    @Test
    @DisplayName("update()는 존재하지 않는 id를 인자로 호출하면 예외를 던진다.")
    void when_update_invoked_with_not_existing_id_then_exception_thrown() {
        categoryService.create(new CategoryDto(CATEGORY_NAME, HIDDEN));

        CategoryDto src = new CategoryDto(UPDATED_CATEGORY_NAME, VISIBLE);
        assertThatThrownBy(() -> categoryService.update(Long.MAX_VALUE, src))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("update()는 이미 존재하는 이미 존재하는 category 이름을 인자로 호출하며 예외를 던진다.")
    void when_update_invoked_with_category_name_already_existing_then_throws_exception() {
        CategoryDto category = categoryService.create(new CategoryDto(CATEGORY_NAME, HIDDEN));

        assertThatThrownBy(() -> categoryService.update(category.getId(), new CategoryDto(CATEGORY_NAME, HIDDEN)))
                .isInstanceOf(DuplicateCategoryException.class);
    }

}
