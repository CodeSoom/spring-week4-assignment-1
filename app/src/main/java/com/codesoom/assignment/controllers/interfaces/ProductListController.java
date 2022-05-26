package com.codesoom.assignment.controllers.interfaces;

import com.codesoom.assignment.controllers.dtos.ToyRequestDto;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;

import java.util.List;

public interface ProductListController {
    List<ToyResponseDto> list();
}
