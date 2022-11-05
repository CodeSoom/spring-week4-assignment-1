package com.codesoom.assignment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    default Boolean isCategoryExisting(String categoryName) {
        return findByName(categoryName).isPresent();
    }
}
