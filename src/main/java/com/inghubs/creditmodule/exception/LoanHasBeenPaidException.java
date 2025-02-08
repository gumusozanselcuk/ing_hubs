package com.inghubs.creditmodule.exception;

public class LoanHasBeenPaidException extends RuntimeException {

	private static final long serialVersionUID = 1298531681040716597L;

	public LoanHasBeenPaidException() {
		super();
	}

	public LoanHasBeenPaidException(String message) {
		super(message);
	}

}
