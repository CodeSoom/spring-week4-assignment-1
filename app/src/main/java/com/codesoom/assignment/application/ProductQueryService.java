package com.codesoom.assignment.application;

import com.codesoom.assignment.controller.dto.ProductResponseDto;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품 조회를 담당합니다.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품을 조회합니다.
     * @param id 상품을 찾을 아이디
     * @return 찾은 상품
     * @throws ProductNotFoundException 해당하는 아이디의 상품을 찾지 못한 경우
     */
    public ProductResponseDto get(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id + "에 해당하는 상품을 찾지 못했습니다."));

        return ProductResponseDto.toDto(product);
    }

    /**
     * 모든 상품을 조회합니다.
     * @return 저장된 모든 상품
     */
    public List<ProductResponseDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
