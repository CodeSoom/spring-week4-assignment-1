package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 고양이 장난감에 대한 Http Request 요청 컨트롤러
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class CatToyController {
    private final CatToyService catToyService;

}
