package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductCrudService;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.controllers.interfaces.ProductListController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyListController implements ProductListController {
    private final ProductCrudService service;

    public ToyListController(ProductCrudService service) {
        this.service = service;
    }

    @GetMapping
    @Override
    public List<ToyResponseDto> list() {
        return service.showAll().stream()
                .map(ToyResponseDto::new)
                .collect(Collectors.toList());
    }
}
