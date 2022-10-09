package com.codesoom.assignment.application.query;

import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.ProductInfo;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 전체 상품 목록을 리턴한다
     * @return 전체 상품 목록
     */
    @Override
    public List<ProductInfo> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductInfo::new)
                .collect(Collectors.toList());
    }

    /**
     * 상품ID에 해당하는 상품을 리턴한다.
     * @param id 상품 ID
     * @return 검색된 상품
     * @throws ProductNotFoundException 상품이 없을 경우 발생하는 예외
     */
    @Override
    public ProductInfo getProduct(Long id) {
        return new ProductInfo(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }
}
