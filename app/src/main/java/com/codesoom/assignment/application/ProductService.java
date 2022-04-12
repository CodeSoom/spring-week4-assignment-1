package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductSaveDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 상품에 대한 요청을 처리합니다.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 전체 목록을 리턴합니다.
     */
    public List<Product> getProducts() {
        Iterable<Product> source = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        source.forEach(products::add);
        return products;
    }

    public Product saveProduct(ProductSaveDto productSaveDto) {
        return null;
    }
}
