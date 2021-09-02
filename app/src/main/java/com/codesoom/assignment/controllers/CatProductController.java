package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.CatProductService;
import com.codesoom.assignment.domain.CatProduct;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class CatProductController {

  private final CatProductService catProductService;

  public CatProductController(CatProductService catProductService){
    this.catProductService = catProductService;
  }

  @GetMapping("")
  public List<CatProduct> getProductList(){
      return catProductService.getCatProducts();

  }
}
