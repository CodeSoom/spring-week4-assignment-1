package com.codesoom.assignment.controllers.interfaces;

import com.codesoom.assignment.controllers.dtos.ToyResponseDto;


public interface ProductDetailController {
    ToyResponseDto detail(Long id);
}
