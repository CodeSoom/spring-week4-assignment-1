package com.codesoom.assignment.application.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;

public interface ProductCrudService {
    List<Toy> showAll();

    Toy showById(Long id);

    Toy create(Toy toy);
}
