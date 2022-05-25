package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;

public interface ProductCrudController {
    List<Toy> list();

    Toy detail(Long id);
}
