package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.service.CatToyService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CatToyController {
    private final CatToyService service;

    public CatToyController(CatToyService service) {
        this.service = service;
    }

    public List<CatToy> getList() {
        return service.getList();
    }
}
