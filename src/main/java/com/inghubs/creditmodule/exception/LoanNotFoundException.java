package com.inghubs.creditmodule.exception;

public class LoanNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1008531732040716597L;

	public LoanNotFoundException() {
		super();
	}

	public LoanNotFoundException(String message) {
		super(message);
	}

}
