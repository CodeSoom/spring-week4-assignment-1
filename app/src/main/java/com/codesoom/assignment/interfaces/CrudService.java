package com.codesoom.assignment.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudService {
    List<Product> showAll();

    Optional<Product> showById(Long id);
}
