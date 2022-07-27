package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.service.CatToyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class CatToyController {
    private final CatToyService service;

    public CatToyController(CatToyService service) {
        this.service = service;
    }

    @GetMapping
    public List<CatToy> getList() {
        return service.getList();
    }
}
