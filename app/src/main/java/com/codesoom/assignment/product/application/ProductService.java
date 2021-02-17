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
 * 상품 정보을 다룬다.
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
        return productRepository.findAll().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 등록된 상품 id를 가진 상품을 리턴한다.
     *
     * @param productId 등록된 상품 id
     * @return 등록된 상품
     */
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return new ProductResponseDto(product);
    }

    @Transactional
    public Long createTask(ProductSaveRequestDto requestDto) {
        // TODO : 상품을 등록하기
        return null;
    }

    /**
     * 등록된 상품의 정보를 갱신한다.
     * @param productId 등록된 상품 id
     * @param requestDto 갱신할 상품 정보
     * @return 갱신된 상품 id
     */
    @Transactional
    public Long updateProduct(long productId, ProductUpdateRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        product.update(requestDto.getName(), requestDto.getMaker(), requestDto.getPrice(), requestDto.getImageUrl());
        return product.getId();
    }

    @Transactional
    public Long deleteProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        productRepository.deleteById(productId);
        return product.getId();
    }
}
