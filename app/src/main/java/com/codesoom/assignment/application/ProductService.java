package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if(foundProduct.isEmpty()) throw new ProductNotFoundException(id);
        return foundProduct.get();
    }

    public Product create(ProductDto productDto) {
        Product newProduct = productDto.toEntity();
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, ProductDto productDto) {
        Product product = getProductById(id);
        product.modify(productDto);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
