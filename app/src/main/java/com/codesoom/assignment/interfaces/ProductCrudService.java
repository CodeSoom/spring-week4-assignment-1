package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;

public interface ProductCrudService {
    List<Toy> showAll();

    Toy showById(Long id);
}
