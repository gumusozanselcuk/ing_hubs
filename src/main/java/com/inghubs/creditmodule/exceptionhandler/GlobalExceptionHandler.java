package com.inghubs.creditmodule.exceptionhandler;

import com.inghubs.creditmodule.dto.BaseResponseEntity;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseResponseEntity {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
			ConstraintViolationException ex) {
		return super.prepareResponseMessage(null, true, ex.getMessage(),
				HttpStatus.BAD_REQUEST);
	}
}