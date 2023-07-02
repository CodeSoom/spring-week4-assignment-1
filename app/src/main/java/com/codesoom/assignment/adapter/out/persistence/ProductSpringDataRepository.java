package com.codesoom.assignment.adapter.out.persistence;

import com.codesoom.assignment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductSpringDataRepository extends JpaRepository<ProductEntity, Long> {
}
