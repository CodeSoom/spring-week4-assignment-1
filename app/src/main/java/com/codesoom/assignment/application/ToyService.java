package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;

import java.util.List;

public interface ToyService {
    List<Toy> getToys();

    Toy getToy(Long id);
}
