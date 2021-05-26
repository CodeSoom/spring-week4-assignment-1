package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exceptions.ProductNotFoundException;
import com.codesoom.assignment.models.domain.Product;
import com.codesoom.assignment.models.domain.ProductRepository;
import com.codesoom.assignment.models.dto.ProductDto;
import com.codesoom.assignment.models.dto.ProductInfoDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품에 관한 CRUD 작업을 담당합니다.
 */
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 목록을 조회합니다.
     * @return 조회한 상품 목록을 반환합니다.
     */
    public List<ProductInfoDto> getProductList() {

        List<Product> productList = productRepository.findAll();

        return productList.stream().map(p -> new ProductInfoDto(p))
                .collect(Collectors.toList());

    }

    /**
     * 주어진 ID에 해당하는 상품을 조회합니다.
     * @param productId 조회할 상품 ID
     * @return 조회된 상품을 반환합니다.
     */
    public ProductInfoDto getProductOne(Long productId) {

        Product foundProduct = productRepository.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return new ProductInfoDto(foundProduct);

    }

    /**
     * 새로운 상품을 등록합니다.
     * @param createParam 등록할 상품 정보
     * @return 등록된 상품을 반환합니다.
     */
    @Transactional
    public ProductDto saveProduct(ProductInfoDto createParam){

        Product willCreateProduct = new Product(createParam.getName(), createParam.getMaker(), createParam.getPrice(), createParam.getImageUrl());
        Product createdProduct = productRepository.save(willCreateProduct);

        return new ProductDto(createdProduct);

    }

    /**
     * 주어진 ID에 해당하는 상품의 정보를 수정합니다.
     * @param productId 수정할 상품 ID
     * @param updateParam 수정할 상품 정보
     * @return 수정된 상품을 반환합니다.
     */
    @Transactional
    public ProductInfoDto updateProduct(Long productId , ProductInfoDto updateParam){

        Product foundProduct = productRepository.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Product updatedProduct = foundProduct.update(updateParam);

        return new ProductInfoDto(updatedProduct);

    }

    /**
     * 주어진 ID에 해당하는 상품을 삭제합니다.
     * @param productId 삭제할 상품 ID
     */
    @Transactional
    public void deleteProduct(Long productId){

        Product foundProduct = productRepository.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        productRepository.delete(foundProduct);

    }



}
