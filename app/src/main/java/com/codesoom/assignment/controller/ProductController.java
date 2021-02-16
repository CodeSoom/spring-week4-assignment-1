package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ProductRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 상품 컨트롤러
 * @Author wenodev
 */
@RequestMapping("/products")
@RestController
public class ProductController {

    @GetMapping
    public List<ProductRequestDto> getProducts(){

    }

}
