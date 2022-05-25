package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.interfaces.CrudController;
import com.codesoom.assignment.interfaces.CrudService;
import com.codesoom.assignment.interfaces.Product;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyCrudController implements CrudController {
    private final CrudService service;

    public ToyCrudController(CrudService service) {
        this.service = service;
    }

    @GetMapping
    @Override
    public List<Toy> list() {
        return service.showAll();
    }

    @Override
    public Optional<Toy> detail(Long id) {
        return service.showById(id);
    }
}
