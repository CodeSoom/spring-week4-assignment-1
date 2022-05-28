package com.codesoom.assignment.service;

import com.codesoom.assignment.controller.ProductNotFoundException;
import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO.Response getProduct(int id) {
        return ProductDTO.Response.of(productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id)));
    }

    @Transactional
    public ProductDTO.Response createProduct(ProductDTO.CreateProduct createProduct) {
        return ProductDTO.Response.of(productRepository.save(new Product(createProduct.getName(),
                createProduct.getPrice(), createProduct.getImageUrl(), createProduct.getMaker())));
    }

    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public List<ProductDTO.Response> getProducts() {
        return productRepository.findAll().stream().map(ProductDTO.Response::of).collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO.Response updateProduct(int id, ProductDTO.UpdateProduct source) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
        return ProductDTO.Response.of(product.updateProduct(source.getName(), source.getPrice(), source.getImageUrl(),
                source.getMaker()));
    }

}
