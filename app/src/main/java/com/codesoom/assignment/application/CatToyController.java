package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.service.CatToyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping("{id}")
    public CatToy findById(@PathVariable Long catToyId) {
        return service.findById(catToyId)
                .orElseThrow(() -> new ToyNotFoundException(catToyId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy save(@RequestBody CatToy newToy) {
        return service.save(newToy);
    }
}
