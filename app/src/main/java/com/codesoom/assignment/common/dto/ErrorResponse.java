package com.codesoom.assignment.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class ErrorResponse {
    private String code;
    private String message;
    private Map<String, String> validation;

    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String field, String message) {
        validation.put(field, message);
    }

}
