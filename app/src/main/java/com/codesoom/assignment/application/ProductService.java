package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.entity.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.models.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return null;
    }

    public Product createProduct(ProductDto productDto) {
        Product product = productRepository.save(productDto.toEntity());
        return product;
    }

    public Product updateProduct(Long id, ProductDto productDto) {
        return null;
    }

    public void deleteProduct(Long id) {
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }
}
