package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.ui.dto.ProductResponseDto;
import com.codesoom.assignment.product.ui.dto.ProductSaveRequestDto;
import com.codesoom.assignment.product.ui.dto.ProductUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    public List<ProductResponseDto> getProducts() {
        return null;
    }

    public ProductResponseDto getProduct(Long productId) {
        return null;
    }

    public Long createTask(ProductSaveRequestDto requestDto) {
        return null;
    }

    public Long updateProduct(long productId, ProductUpdateRequestDto requestDto) {
        return null;
    }
}
