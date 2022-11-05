package com.codesoom.assignment.products.application;

import com.codesoom.assignment.exception.products.ProductNotFoundException;
import com.codesoom.assignment.products.controllers.dto.request.ProductCreateRequest;
import com.codesoom.assignment.products.controllers.dto.request.ProductUpdateRequest;
import com.codesoom.assignment.products.domain.Product;
import com.codesoom.assignment.products.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품의 목록을 리턴합니다.
     *
     * @return 상품 목록 리턴
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 상품의 상세 정보를 리턴합니다.
     *
     * @param id 상품 고유 id
     * @return 상품 상세 정보 리턴
     */
    public Product getProduct(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    /**
     * 상품을 등록하고 리턴합니다.
     *
     * @param productCreateRequest 등록할 상품 정보
     * @return 등록한 상품 상세 정보 리턴
     */
    public Product createProduct(final ProductCreateRequest productCreateRequest) {
        return productRepository.save(productCreateRequest.toEntity());
    }

    /**
     * 상품을 수정하고 리턴합니다.
     *
     * @param id                   상품 고유 id
     * @param productUpdateRequest 수정할 상품 정보
     * @return 수정한 상품 상세 정보 리턴
     */
    public Product updateProduct(final Long id,
                                 final ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        product.update(productUpdateRequest.toEntity());

        return product;
    }

    /**
     * 상품을 삭제합니다.
     *
     * @param id 상품 고유 id
     */
    public void deleteProduct(final Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.delete(product);
    }
}
