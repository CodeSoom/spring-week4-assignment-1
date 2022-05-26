package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductCreateService;
import com.codesoom.assignment.controllers.dtos.ToyRequestDto;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.controllers.interfaces.ProductCreateController;
import com.codesoom.assignment.domain.Toy;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyCreateController implements ProductCreateController {
    private final ProductCreateService service;

    public ToyCreateController(ProductCreateService service) {
        this.service = service;
    }

    @PostMapping
    @Override
    public ToyResponseDto create(ToyRequestDto requestDto) {
        Toy toy = requestDto.toEntity();
        return new ToyResponseDto(service.create(toy));
    }
}
