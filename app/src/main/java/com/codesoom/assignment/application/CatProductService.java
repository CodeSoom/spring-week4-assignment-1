package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatProduct;
import com.codesoom.assignment.domain.CatProductRepository;
import java.util.List;
import javassist.NotFoundException;

public class CatProductService {
  private final CatProductRepository catProductRepository;

  public CatProductService(CatProductRepository catProductRepository){
    this.catProductRepository = catProductRepository;
  }


    public List<CatProduct> getCatProducts{
    return catProductRepository.findAll();
  }

}
