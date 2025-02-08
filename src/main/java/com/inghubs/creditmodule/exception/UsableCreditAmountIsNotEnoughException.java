package com.inghubs.creditmodule.exception;

public class UsableCreditAmountIsNotEnoughException extends RuntimeException {

	private static final long serialVersionUID = 2138584643040716507L;

	public UsableCreditAmountIsNotEnoughException() {
		super();
	}

	public UsableCreditAmountIsNotEnoughException(String message) {
		super(message);
	}

}
