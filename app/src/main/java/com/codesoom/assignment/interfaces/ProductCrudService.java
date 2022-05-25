package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;
import java.util.Optional;

public interface ProductCrudService {
    List<Toy> showAll();

    Optional<Toy> showById(Long id);
}
