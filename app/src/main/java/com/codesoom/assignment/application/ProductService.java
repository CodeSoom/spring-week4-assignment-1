package com.codesoom.assignment.application;


import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product source){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));

        product.setName(source.getName());
        product.setMaker(source.getMaker());
        product.setPrice(source.getPrice());
        product.setImageUrl(source.getImageUrl());

        return product;
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

}
