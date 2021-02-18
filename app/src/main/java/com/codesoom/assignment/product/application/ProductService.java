package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.infra.ProductRepository;
import com.codesoom.assignment.product.ui.dto.ProductResponseDto;
import com.codesoom.assignment.product.ui.dto.ProductSaveRequestDto;
import com.codesoom.assignment.product.ui.dto.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품 정보를 다룬다.
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 등록된 모든 상품 목록을 가져온다.
     */
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 등록된 상품 id를 가진 상품을 리턴한다.
     *
     * @param productId 등록된 상품 id
     * @return 등록된 상품
     */
    public ProductResponseDto getProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return ProductResponseDto.of(product);
    }

    /**
     * 상품을 등록하고, 등록된 정보를 리턴한다.
     *
     * @param requestDto 등록할 상품 정보
     * @return 등록된 상품정보
     */
    @Transactional
    public ProductResponseDto createProduct(ProductSaveRequestDto requestDto) {
        Product saveProduct = productRepository.save(requestDto.toEntity());
        return ProductResponseDto.of(saveProduct);
    }

    /**
     * 등록된 상품의 정보를 갱신하고, 갱신된 정보를 리턴한다.
     *
     * @param productId  등록된 상품 id
     * @param requestDto 갱신할 상품 정보
     * @return 갱신된 상품 정보
     */
    @Transactional
    public ProductResponseDto updateProduct(Long productId,
                                            ProductUpdateRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Product updateProduct = product.update(
                requestDto.getName(),
                requestDto.getMaker(),
                requestDto.getPrice(),
                requestDto.getImageUrl());
        return ProductResponseDto.of(updateProduct);
    }

    /**
     * 등록된 상품을 삭제한다.
     *
     * @param productId 등록된 상품 id
     * @return 삭제된 상품 id
     */
    @Transactional
    public Long deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        productRepository.deleteById(productId);
        return product.getId();
    }
}
