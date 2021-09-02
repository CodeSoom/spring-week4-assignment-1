package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToyViewModel;
import com.codesoom.assignment.dto.CatToyModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 고양이 장난감 생성, 조회, 수정, 삭제 API
 */
@RestController
@RequestMapping(value = "/products")
public class CatToyController {

    private final CatToyService catToyService;

    public CatToyController(CatToyService catToyService) {
        this.catToyService = catToyService;
    }


    /**
     * 고양이 장난감을 생성 후 리턴한다.
     * @param request
     * @return 고양이 장난감
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CatToyViewModel.Response createCatToy(@RequestBody CatToyViewModel.Request request) {
        CatToyModel catToy = catToyService.createCatToy(new CatToyModel(request.getName(), request.getMaker(), request.getPrice(), request.getImageUrl()));
        return new CatToyViewModel.Response(catToy);
    }

}
