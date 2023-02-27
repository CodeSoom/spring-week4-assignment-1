package com.codesoom.assignment.common.global;

import com.codesoom.assignment.common.exception.NotFoundMakerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn("MethodArgumentNotValidException : ", e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        logger.warn("MethodArgumentTypeMismatchException : ", e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleConversionFailedException(ConversionFailedException e) {
        logger.warn("ConversionFailedException : ", e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.warn("HttpMessageNotReadableException : ", e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleNumberFormatException(NumberFormatException e) {
        logger.warn("NumberFormatException : ", e);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIllegalArgumentException(IllegalArgumentException e) {
        logger.warn("IllegalArgumentException : ", e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBeanInstantiationException(BeanInstantiationException e) {
        logger.warn("BeanInstantiationException : ", e);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundMakerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleAuthorizationServiceException(NotFoundMakerException e) {
        logger.warn("NotFoundMakerException : " , e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleRuntimeException(RuntimeException e) {
        logger.error("RuntimeException : ", e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception e) {
        logger.error("Exception : ", e);
    }
}
