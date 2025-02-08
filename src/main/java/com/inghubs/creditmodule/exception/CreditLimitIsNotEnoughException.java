package com.inghubs.creditmodule.exception;

public class CreditLimitIsNotEnoughException extends RuntimeException {

	private static final long serialVersionUID = 7498584689040716507L;

	public CreditLimitIsNotEnoughException() {
		super();
	}

	public CreditLimitIsNotEnoughException(String message) {
		super(message);
	}

}
