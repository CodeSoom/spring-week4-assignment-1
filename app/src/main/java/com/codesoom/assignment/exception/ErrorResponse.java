package com.codesoom.assignment.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Getter
@Builder
@Slf4j
public class ErrorResponse {
    private final LocalDateTime occuredTime;
    private final String message;

    public static ErrorResponse from(final CommonException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .occuredTime(exception.getOccuredTime())
                .build();
    }

    public static ErrorResponse from(HttpServletRequest request, final Exception exception) {
        log.error("[ERROR-]\t{}\t{}\t{}", request.getMethod(), request.getRequestURI(), exception.getMessage());
        log.error("{}", (Object) exception.getStackTrace());

        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }
}
