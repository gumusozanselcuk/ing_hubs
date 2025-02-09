package com.inghubs.creditmodule.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Base response entity that contains data, error and message
 */
public class BaseResponseEntity {

	/**
	 * Prepare response message by data, error, message and http status
	 *
	 * @param data response data
	 * @param error response error info
	 * @param message response message
	 * @param httpStatus response httpStatus info
	 * @return the ResponseEntity
	 */
	protected <T> ResponseEntity<Map<String, Object>> prepareResponseMessage(T data, boolean error, String message, HttpStatus httpStatus) {
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("data", data);
		responseMap.put("error", error);
		responseMap.put("message", message);

		return new ResponseEntity<>(responseMap, httpStatus);
	}

}
