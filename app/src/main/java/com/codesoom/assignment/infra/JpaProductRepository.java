package com.codesoom.assignment.infra;

import com.codesoom.assignment.models.Product;
import com.codesoom.assignment.models.ProductRepository;
import com.codesoom.assignment.models.Task;
import com.codesoom.assignment.models.TaskRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Primary
public interface JpaProductRepository
        extends ProductRepository, CrudRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(Long id);


}
