package com.codesoom.assignment.controller;

import com.codesoom.assignment.dto.ProductResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상품 컨트롤러
 * @Author wenodev
 */
@RequestMapping("/products")
@RestController
public class ProductController {

    @GetMapping
    public List<ProductResponseDto> getProducts(){
        return null;
    }

}
