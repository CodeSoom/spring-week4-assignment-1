package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));
    }

    public Product createProduct(Product source) {
        return productRepository.save(source);
    }

    public Product updateProduct(Long id, Product source) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));
        //더티 체킹
        entity.update(source.getName(), source.getMaker(), source.getPrice(), source.getImagePath());
        return entity;
    }

    public Product deleteProduct(Long id) {
        Product product = this.getProduct(id);
        productRepository.deleteById(id);
        return product;
    }
}
