package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 상품과 관련된 비즈니스 로직을 처리합니다.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 목록을 조회해 리턴합니다.
     * @return 조회한 상품 목록
     */
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    /**
     * 상품을 조회해 리턴합니다 .
     * @param productId 조회할 상품의 Id
     * @return 조회한 상품
     */
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    /**
     * 새 상품을 등록하고 리턴합니다.
     * @param newProduct 새로 등록할 상품 내용
     * @return 등록한 상품
     */
    public Product createProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    /**
     * 상품을 수정하고 리턴합니다.
     * @param productId 수정할 상품 Id
     * @param product 수정할 상품 내용
     * @return 수정한 상품
     */
    public Product updateProduct(Long productId, Product product) {
        Product foundProduct = getProduct(productId);
        Product updatedProduct = updateFoundProduct(foundProduct, product);
        return productRepository.save(updatedProduct);
    }

    /**
     * 상품을 삭제 합니다.
     * @param productId 삭제할 상품 Id
     */
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
