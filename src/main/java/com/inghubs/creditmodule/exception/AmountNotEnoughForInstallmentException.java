package com.inghubs.creditmodule.exception;

public class AmountNotEnoughForInstallmentException extends RuntimeException {

	private static final long serialVersionUID = 1298531681040716597L;

	public AmountNotEnoughForInstallmentException() {
		super();
	}

	public AmountNotEnoughForInstallmentException(String message) {
		super(message);
	}

}
