package com.codesoom.assignment.product.application.port.in;

import com.codesoom.assignment.product.adapter.in.web.dto.ProductCreateRequest;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductUpdateRequest;
import com.codesoom.assignment.product.domain.Product;

import java.util.List;

public interface ProductUseCase {
    /**
     * 상품의 목록을 리턴합니다.
     *
     * @return 상품 목록 리턴
     */
    List<Product> getProducts();

    /**
     * 상품의 상세 정보를 리턴합니다.
     *
     * @param id 상품 고유 id
     * @return 상품 상세 정보 리턴
     */
    Product getProduct(final Long id);

    /**
     * 상품을 등록하고 리턴합니다.
     *
     * @param productCreateRequest 등록할 상품 정보
     * @return 등록한 상품 상세 정보 리턴
     */
    Product createProduct(final ProductCreateRequest productCreateRequest);

    /**
     * 상품을 수정하고 리턴합니다.
     *
     * @param id                   상품 고유 id
     * @param productUpdateRequest 수정할 상품 정보
     * @return 수정한 상품 상세 정보 리턴
     */
    Product updateProduct(final Long id, final ProductUpdateRequest productUpdateRequest);

    /**
     * 상품을 삭제합니다.
     *
     * @param id 상품 고유 id
     */
    void deleteProduct(final Long id);
}
