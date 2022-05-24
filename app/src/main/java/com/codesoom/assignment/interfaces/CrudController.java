package com.codesoom.assignment.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudController {
    List<Product> list();

    Optional<Product> detail(Long id);
}
