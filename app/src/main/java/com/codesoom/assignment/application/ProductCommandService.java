package com.codesoom.assignment.application;

import com.codesoom.assignment.controller.dto.ProductResponseDto;
import com.codesoom.assignment.controller.dto.ProductUpdateRequest;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 상품 생성/수정/삭제를 처리합니다.
 */
@Service
@Transactional
public class ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품을 생성하여 반환합니다.
     * @param source 생성할 상품
     * @return 생성된 상품
     */
    public ProductResponseDto create(ProductUpdateRequest source) {
        Product product = new Product(source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImageUrl()
        );

        return new ProductResponseDto(productRepository.save(product));
    }

    /**
     * 상품을 수정하여 반환합니다.
     * @Param id 수정할 상품 아이디
     * @param source 수정할 상품
     * @return 수정된 상품
     * @throws ProductNotFoundException 수정할 상품을 찾지 못한 경우
     */
    public ProductResponseDto update(Long id, ProductUpdateRequest source) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id + "에 해당하는 상품을 찾지 못해 업데이트할 수 없습니다."));

        product.update(source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImageUrl()
        );

        return new ProductResponseDto(product);
    }

    /**
     * 상품 하나를 삭제합니다.
     * @param id 삭제할 상품 아이디
     * @throws ProductNotFoundException 삭제할 상품을 찾지 못한 경우
     */
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id + "에 해당하는 상품을 찾지 못해 삭제할 수 없습니다."));

        productRepository.delete(product);
    }

    /**
     * 상품 여러 개를 삭제합니다.
     * @param ids 삭제할 상품 아이디 목록
     * @throws ProductNotFoundException 삭제할 상품을 찾지 못한 경우
     */
    public void deleteByIds(Set<Long> ids) {
        for (Long id : ids) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id + "에 해당하는 상품을 찾지 못해 삭제할 수 없습니다."));

            productRepository.delete(product);
        }
    }
}
