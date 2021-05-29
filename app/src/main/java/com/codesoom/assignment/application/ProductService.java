package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductManagable {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return findById(id);

    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product sourceProduct) {
        Product product = findById(id);
        product.updateBy(sourceProduct);
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(Math.toIntExact(id)));
    }
}
