package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductRequest;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품과 관련된 비즈니스 로직을 담당합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 모든 상품을 리턴합니다.
     */
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 id에 해당하는 상품을 찾아 리턴합니다.
     *
     * @param id 찾고자 하는 상품의 식별자
     * @return 주어진 id에 해당하는 상품
     * @throws ProductNotFoundException 주어진 id에 해당하는 상품을 찾지 못했을 경우
     */
    public ProductResponse getProduct(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("존재하지 않는 상품 id가 주어졌으므로 상품을 찾을 수 없습니다. 문제의 id = " + id));

        return new ProductResponse(product);
    }

    /**
     * 주어진 상품을 저장한 뒤, 저장된 상품을 리턴합니다.
     *
     * @param productRequest 저장하고자 하는 상품
     * @return 저장된 상품
     */
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product savedProduct = productRepository.save(productRequest.toProduct());

        return new ProductResponse(savedProduct);
    }

    /**
     * 주어진 id에 해당하는 상품을 찾아 수정하고, 수정된 상품을 리턴합니다.
     *
     * @param id 수정하고자 하는 상품의 식별자
     * @param productRequest 수정하고자 하는 상품
     * @return 수정된 상품
     * @throws ProductNotFoundException 주어진 id에 해당하는 상품을 찾지 못했을 경우
     */
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest)
            throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("존재하지 않는 상품 id가 주어졌으므로 상품을 수정할 수 없습니다. 문제의 id = " + id));

        Product updatedProduct = product.update(productRequest.toProduct());

        return new ProductResponse(updatedProduct);
    }

    /**
     * 주어진 id에 해당하는 상품을 찾아 삭제합니다.
     *
     * @param id 삭제하고자 하는 상품의 식별자
     * @throws ProductNotFoundException 주어진 id에 해당하는 상품을 찾지 못했을 경우
     */
    @Transactional
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("존재하지 않는 상품 id가 주어졌으므로 상품을 삭제할 수 없습니다. 문제의 id = " + id));

        productRepository.delete(product);
    }

}
