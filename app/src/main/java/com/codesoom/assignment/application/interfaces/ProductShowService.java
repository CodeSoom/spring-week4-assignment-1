package com.codesoom.assignment.application.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;

public interface ProductShowService {
    List<Toy> showAll();

    Toy showById(Long id);
}
