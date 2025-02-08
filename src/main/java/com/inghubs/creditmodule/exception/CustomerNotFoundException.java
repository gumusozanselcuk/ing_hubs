package com.inghubs.creditmodule.exception;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5498581681640716507L;

	public CustomerNotFoundException() {
		super();
	}

	public CustomerNotFoundException(String message) {
		super(message);
	}

}
