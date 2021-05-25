package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//ToDo : ProductService 구현
// 고양이 장난감 전체 조회 -> getCatToys()
// 고양이 장난감 상세 조회 -> getCatToy(id)
// 고양이 장난감 등록하기 -> createCatToy(CatToy)
// 고양이 장난감 수정하기 - updateCatToy(CatToy)findById -> save
// 고양이 장난감 삭제하기 -> delete

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .stream().findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product saveProduct(Product source) {
        Product product = productRepository.save(source);
        return product;
    }

    public Product updateProduct(Long id, Product source) {
        Product product = productRepository
                .findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.save(source);
        return source;
    }

    public Product deleteProduct(Long id) {
        Product product = productRepository.
                findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
        return product;
    }
}
