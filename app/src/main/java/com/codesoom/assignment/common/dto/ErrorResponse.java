package com.codesoom.assignment.common.dto;

import com.codesoom.assignment.common.ErrorValidation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ErrorResponse {
    private String code;
    private String message;
    private List<ErrorValidation> errors;

    public ErrorResponse(String code, String message, List<ErrorValidation> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors != null ? errors : List.of();
    }

}
