package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;
import java.util.Optional;

public interface ProductCrudController {
    List<Toy> list();

    Optional<Toy> detail(Long id);
}
