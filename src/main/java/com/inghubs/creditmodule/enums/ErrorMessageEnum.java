package com.inghubs.creditmodule.enums;

public enum ErrorMessageEnum {

	CUSTOMER_NOT_FOUND("There is no customer with given id."),
	CREDIT_LIMIT_IS_NOT_ENOUGH("Your credit limit is below your desired credit amount."),
	INCORRECT_PARAMETERS("There is an error or incompleteness in the parameters sent."),
	USABLE_CREDIT_AMOUNT_IS_NOT_ENOUGH("Your available credit amount is below the desired amount."),
	LOAN_NOT_FOUND("There is no loan with given id."),
	LOAN_HAS_ALREADY_BEEN_PAID("The loan has already been paid."),
	AMOUNT_NOT_ENOUGH_FOR_INSTALLMENT("The amount you sent is not enough to pay an installment.");


	private String value;

	ErrorMessageEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
