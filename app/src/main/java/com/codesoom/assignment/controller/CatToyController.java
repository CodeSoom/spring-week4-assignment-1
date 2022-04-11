package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  고양이 장난감에 대한 HTTP 요청을 처리합니다.
 */
@RestController
@RequestMapping("/products")
public class CatToyController {

    private final CatToyService catToyService;

    public CatToyController(CatToyService catToyService) {
        this.catToyService = catToyService;
    }

    /**
     *  고양이 장난감 전체 목록을 리턴합니다.
     */
    @GetMapping
    public List<CatToy> list() {
        return catToyService.getCatToys();
    }
}
