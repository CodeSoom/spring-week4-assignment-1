package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품에 대한 생성, 수정, 삭제 등의 작업을 수행한다.
 */
@Service
@Transactional
public class ProductCommandService {

    private final ProductRepository productRepository;


    public ProductCommandService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product create(ProductDto productDto) {
        Product newProduct = productDto.toEntity();
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                                .orElseThrow(() -> new ProductNotFoundException(id));
        product.modify(productDto);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
