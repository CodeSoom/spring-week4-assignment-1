package com.codesoom.assignment.controllers;

import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.interfaces.ProductCrudController;
import com.codesoom.assignment.interfaces.ProductCrudService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<Toy> list() {
        return service.showAll();
    }

    @Override
    public ToyResponseDto detail(Long id) {
        Toy toy = service.showById(id);
        return new ToyResponseDto(toy);
    }
}
