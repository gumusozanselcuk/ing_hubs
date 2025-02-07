package com.inghubs.creditmodule.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BaseResponseEntity {

	protected <T> ResponseEntity<Map<String, Object>> prepareResponseMessage(T data, boolean error, String message, HttpStatus httpStatus) {
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("data", data);
		responseMap.put("error", error);
		responseMap.put("message", message);

		return new ResponseEntity<>(responseMap, httpStatus);
	}

}
