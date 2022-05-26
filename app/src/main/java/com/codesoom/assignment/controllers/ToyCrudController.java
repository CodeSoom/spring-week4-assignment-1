package com.codesoom.assignment.controllers;

import com.codesoom.assignment.controllers.dtos.ToyRequestDto;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.controllers.interfaces.ProductCrudController;
import com.codesoom.assignment.application.interfaces.ProductCrudService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyCrudController implements ProductCrudController {
    private final ProductCrudService service;

    public ToyCrudController(ProductCrudService service) {
        this.service = service;
    }

    @GetMapping
    @Override
    public List<ToyResponseDto> list() {
        return service.showAll().stream()
                .map(ToyResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ToyResponseDto detail(Long id) {
        Toy toy = service.showById(id);
        return new ToyResponseDto(toy);
    }

    @Override
    public ToyResponseDto create(ToyRequestDto requestDto) {
        Toy toy = requestDto.toEntity();
        return new ToyResponseDto(service.create(toy));
    }
}
