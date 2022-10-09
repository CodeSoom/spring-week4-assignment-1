package com.codesoom.assignment.application.command;

import com.codesoom.assignment.domain.ProductCommand;
import com.codesoom.assignment.domain.ProductInfo;

public interface ProductCommandService {

    /**
     * 새로운 상품을 추가하고 추가된 상품을 리턴한다.
     * @param command 새로운 상품정보
     * @return 추가된 상품
     */
    ProductInfo createProduct(ProductCommand.Register command);

    /**
     * 상품을 수정하고 수정된 상품을 리턴한다.
     * @param command 수정할 상품정보
     * @return 수정된 상품
     */
    ProductInfo updateProduct(ProductCommand.UpdateReq command);

    /**
     * 상품ID에 해당하는 상품을 검색하고 해당 상품을 삭제한다.
     * @param id 상품 ID
     */
    void deleteProduct(Long id);
}

