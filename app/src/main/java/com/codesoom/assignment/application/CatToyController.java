package com.codesoom.assignment.application;

import com.codesoom.assignment.ToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.service.CatToyFindService;
import com.codesoom.assignment.service.CatToyEditService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class CatToyController {
    private final CatToyFindService findService;
    private final CatToyEditService editService;

    public CatToyController(CatToyFindService findService, CatToyEditService editService) {
        this.findService = findService;
        this.editService = editService;
    }

    @GetMapping
    public List<CatToy> getList() {
        return findService.findAll();
    }

    @GetMapping("{toyId}")
    public CatToy findById(@PathVariable Long toyId) {
        return findService.findById(toyId)
                .orElseThrow(() -> new ToyNotFoundException(toyId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy save(@RequestBody CatToy newToy) {
        return editService.save(newToy);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, path = "{toyId}")
    public CatToy update(@PathVariable Long toyId, @RequestBody CatToy newToy) {
        return editService.update(toyId, newToy);
    }
}
