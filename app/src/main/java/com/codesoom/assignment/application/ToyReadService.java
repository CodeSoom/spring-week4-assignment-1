package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;

import java.util.List;

public interface ToyReadService {

    List<Toy> findAll();

    Toy findById(Long id);

}
