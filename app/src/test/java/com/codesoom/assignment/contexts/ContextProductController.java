package com.codesoom.assignment.contexts;

import com.codesoom.assignment.domains.ProductReqDto;

public abstract class ContextProductController extends ContextProduct {

    protected String productIdJsonString(Long productId) {
        return String.format("\"productId\":%d", productId);
    }

    protected String productJsonString(Long productId, String productName) {
        return String.format("{\"productId\":%d,\"name\":\"%s\"}", productId, productName);
    }

    protected String catTowerJsonString(ProductReqDto newProductInputReq) {
        return String.format("{\"name\":%s, \"maker\":%s, \"price\":%d, \"image\":%s}",
                newProductInputReq.getName(),
                newProductInputReq.getMaker(),
                newProductInputReq.getPrice(),
                newProductInputReq.getImage());
    }

}
