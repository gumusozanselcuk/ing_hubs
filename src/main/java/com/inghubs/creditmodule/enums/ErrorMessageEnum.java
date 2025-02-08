package com.inghubs.creditmodule.enums;

public enum ErrorMessageEnum {

	CUSTOMER_NOT_FOUND("There is no customer with given id."),
	CREDIT_LIMIT_IS_NOT_ENOUGH("Your credit limit is below your desired credit amount."),
	USABLE_CREDIT_AMOUNT_IS_NOT_ENOUGH("Your available credit amount is below the desired amount.");


	private String value;

	ErrorMessageEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
