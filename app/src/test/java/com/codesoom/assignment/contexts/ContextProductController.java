package com.codesoom.assignment.contexts;

import com.codesoom.assignment.domains.Product;
import com.codesoom.assignment.dto.ProductReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ContextProductController extends ContextProduct {

    private final ObjectMapper objectMapper;

    public ContextProductController() {
        objectMapper = new ObjectMapper();
    }

    protected String productJsonString(Product product) throws JsonProcessingException {
        return objectMapper.writeValueAsString(product);
    }

    protected String productReqJsonString(ProductReqDto productReqDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(productReqDto);
    }

}
