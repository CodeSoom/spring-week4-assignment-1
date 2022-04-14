package com.codesoom.assignment.contexts;

public abstract class ContextProductController extends ContextProduct {

    protected String productIdJsonString(Long productId) {
        return String.format("\"productId\":%d", productId);
    }

    protected String productJsonString(Long productId, String productName) {
        return String.format("{\"productId\":%d,\"name\":\"%s\"}", productId, productName);
    }

}
