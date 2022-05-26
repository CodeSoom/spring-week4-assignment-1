package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.domain.Toy;

import java.util.List;

public interface ProductCrudController {
    List<ToyResponseDto> list();

    ToyResponseDto detail(Long id);
}
