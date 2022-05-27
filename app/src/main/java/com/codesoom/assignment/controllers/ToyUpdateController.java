package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductUpdateService;
import com.codesoom.assignment.controllers.dtos.ToyRequestDto;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.controllers.interfaces.ProductUpdateController;
import com.codesoom.assignment.domain.Toy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyUpdateController implements ProductUpdateController {
    private final ProductUpdateService service;

    public ToyUpdateController(ProductUpdateService service) {
        this.service = service;
    }

    @PatchMapping("/{id}")
    @Override
    public ToyResponseDto update(Long id, ToyRequestDto requestDto) {
        Toy toy = service.update(id, requestDto.toEntity());
        return new ToyResponseDto(toy);
    }
}
