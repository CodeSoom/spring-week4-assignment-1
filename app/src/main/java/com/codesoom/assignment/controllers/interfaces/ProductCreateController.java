package com.codesoom.assignment.controllers.interfaces;

import com.codesoom.assignment.controllers.dtos.ToyRequestDto;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;

public interface ProductCreateController {
    ToyResponseDto create(ToyRequestDto requestDto);
}
