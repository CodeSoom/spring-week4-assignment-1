package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public Product createProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long productId, Product product) {
        Product foundProduct = getProduct(productId);
        Product updatedProduct = updateFoundProduct(foundProduct, product);
        return productRepository.save(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        Product foundProduct = getProduct(productId);
        productRepository.delete(foundProduct);
    }

    private Product updateFoundProduct(Product existedProduct, Product product) {
        existedProduct.setName(product.getName());
        existedProduct.setMaker(product.getMaker());
        existedProduct.setPrice(product.getPrice());
        existedProduct.setImageUrl(product.getImageUrl());

        return existedProduct;
    }
}
