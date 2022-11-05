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

    /**
     * 에러의 응답 body를 공통 포맷에 맞게 변경하여 리턴합니다.
     * @param exception CommonException을 상속받는 예외
     * @return 에러 응답 body 리턴
     */
    public static ErrorResponse from(final CommonException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .occuredTime(exception.getOccuredTime())
                .build();
    }

    /**
     * 에러의 응답 body를 공통 포맷에 맞게 변경하여 리턴합니다.
     * @param request 요청 정보
     * @param exception 모든 예외
     * @return 에러 응답 body 리턴
     */
    public static ErrorResponse from(HttpServletRequest request, final Exception exception) {
        log.error("[ERROR-]\t{}\t{}\t{}", request.getMethod(), request.getRequestURI(), exception.getMessage());
        log.error("{}", (Object) exception.getStackTrace());

        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }
}
