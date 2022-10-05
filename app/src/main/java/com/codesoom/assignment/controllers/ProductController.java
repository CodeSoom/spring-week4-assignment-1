package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ToyController {

//    * 고양이 장난감 목록 얻기 - `GET /products`
//    * 고양이 장난감 상세 조회하기 - `GET /products/{id}`
//    * 고양이 장난감 등록하기 - `POST /products`
//    * 고양이 장난감 수정하기 - `PATCH /products/{id}`
//    * 고양이 장난감 삭제하기 - `DELETE /products/{id}`

    private final ToyService toyService;

    public ToyController(ToyService toyService) {
        this.toyService = toyService;
    }

}
