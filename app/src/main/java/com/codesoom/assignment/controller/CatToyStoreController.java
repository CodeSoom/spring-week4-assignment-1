package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyStoreService;
import com.codesoom.assignment.domain.CatToy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class CatToyStoreController {

// TODO
//    고양이 장난감 목록 얻기 - GET /products
//    고양이 장난감 상세 조회하기 - GET /products/{id}
//    고양이 장난감 등록하기 - POST /products
//    고양이 장난감 수정하기 - PATCH /products/{id}
//    고양이 장난감 삭제하기 - DELETE /products/{id}


    private CatToyStoreService catToyStoreService;

    CatToyStoreController(CatToyStoreService catToyStoreService){
        this.catToyStoreService = catToyStoreService;
    }

    @GetMapping("")
    public List<CatToy> list(){

        return catToyStoreService.list();
    }

    @GetMapping("{id}")
    public CatToy detail(@PathVariable Long id){

        return catToyStoreService.detail(id);

    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy create(@RequestBody CatToy catToy){

        return catToyStoreService.create(catToy);
    }

    @PatchMapping("{id}")
    public CatToy update(@PathVariable Long id, @RequestBody CatToy catToy){

        return catToyStoreService.update(id,catToy);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){

        catToyStoreService.delete(id);
    }


}
