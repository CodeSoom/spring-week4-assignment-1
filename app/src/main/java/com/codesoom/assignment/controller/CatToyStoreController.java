package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class CatToyStoreController {

// TODO
//    고양이 장난감 목록 얻기 - GET /products
//    고양이 장난감 상세 조회하기 - GET /products/{id}
//    고양이 장난감 등록하기 - POST /products
//    고양이 장난감 수정하기 - PATCH /products/{id}
//    고양이 장난감 삭제하기 - DELETE /products/{id}

    @GetMapping("")
    public List<CatToy> list(){


        return null;
    }

    @GetMapping("{id}")
    public CatToy detail(@PathVariable Long id){

        return null;
    }

    @PostMapping("")
    public CatToy create(CatToy catToy){

        return null;
    }

    @PatchMapping("{id}")
    public CatToy update(@PathVariable Long id, CatToy catToy){

        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
    }


}
