package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.infra.ProductRepository;
import com.codesoom.assignment.product.ui.dto.ProductResponseDto;
import com.codesoom.assignment.product.ui.dto.ProductSaveRequestDto;
import com.codesoom.assignment.product.ui.dto.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자 요청을 받아 상품 정보을 다룬다.
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProduct(Long productId) {
        // TODO : 응답용 상품을 리턴
        return null;
    }

    public Long createTask(ProductSaveRequestDto requestDto) {
        // TODO : 상품을 등록하기
        return null;
    }

    public Long updateProduct(long productId, ProductUpdateRequestDto requestDto) {
        // TODO : 상품정보를 갱신하기
        return null;
    }

    public Long deleteProduct(long productId) {
        // TODO : 상품을 삭제하기
        return null;
    }
}
