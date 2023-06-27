package com.codesoom.assignment.handler;

import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerHandler {

	@ResponseBody
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> runtimeErrorResponse(CustomException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return new ResponseEntity<>(errorResponse, e.getStatus());
	}
}
