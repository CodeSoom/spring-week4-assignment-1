package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.ui.dto.ProductResponseDto;
import com.codesoom.assignment.product.ui.dto.ProductSaveRequestDto;
import com.codesoom.assignment.product.ui.dto.ProductUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 사용자 요청을 받아 상품 정보을 다룬다.
 */
@Service
public class ProductService {
    public List<ProductResponseDto> getProducts() {
        // TODO : 응답용 상품 목록을 리턴
        return null;
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
}
