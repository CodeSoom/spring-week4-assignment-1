package com.codesoom.assignment.application;

import com.codesoom.assignment.entity.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Product service.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Instantiates a new Product service.
     *
     * @param productRepository the product repository
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Create product.
     *
     * @param product the product
     * @return the product
     */
    public Product create(Product product) {
       return productRepository.save(product);
    }

    /**
     * Find by id product.
     *
     * @param id the id
     * @return the product
     */
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("해당 id를 가진 상품이 존재하지 않습니다 id:" + id));
    }

    /**
     * Gets list.
     *
     * @return the list
     */
    public List<Product> getList() {
        return productRepository.findAll();
    }

    /**
     * Remove product.
     *
     * @param id the id
     * @return the product
     */
    public Product remove(Long id) {
        Product product = productRepository.findById(id).get();
        productRepository.deleteById(id);
        return product;
    }

    /**
     * update product
     *
     * @param id
     * @param product
     * @return the updated product
     */
    public Product update(Long id, Product product){
        return productRepository.findById(id)
            .map(i->i.updateProduct(product))
            .orElseThrow(() -> new ProductNotFoundException("해당 id를 가진 상품이 존재하지 않습니다 id:" + id));
    }

}
