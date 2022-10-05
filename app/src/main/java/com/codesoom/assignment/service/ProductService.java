package com.codesoom.assignment.service;

import com.codesoom.assignment.Exception.ErrorCode;
import com.codesoom.assignment.Exception.ProductException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * responsible for the core business logics of Product Entity
 */
@Service
public class ProductService {
    private final ProductJpaRepository productJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    /**
     * returns a list of all products.
     * @return a list of products
     */
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    /**
     * searches a product given the id
     * @param id the Long to find a product
     * @return Product entity; otherwise, throw an exception
     */
    public Product findById(Long id) throws ProductException {
        return productJpaRepository
                .findById(id)
                .orElseThrow(
                        () -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND, ErrorCode.PRODUCT_NOT_FOUND.getDescription()));
    }

    /**
     * stores a product to the database.
     * @param product the Product to save in the database
     * @return a saved product
     */
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    /**
     * deletes all data in the database.
     * @return an empty list; otherwise, a list with products
     */
    public List<Product> deleteAll() {
        productJpaRepository.deleteAll();
        return productJpaRepository.findAll();
    }
}
