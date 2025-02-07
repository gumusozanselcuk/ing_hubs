package com.inghubs.creditmodule.enums;

public enum MessageEnum {

	CUSTOMER_LOANS_RETURNED_SUCCESSFULLY("Loans of customer returned successfully."),
	LOAN_INSTALLMENTS_RETURNED_SUCCESSFULLY("Installments of loan returned successfully.");


	private String value;

	MessageEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
