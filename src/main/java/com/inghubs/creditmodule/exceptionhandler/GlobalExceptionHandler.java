package com.inghubs.creditmodule.exceptionhandler;

import com.inghubs.creditmodule.dto.BaseResponseEntity;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseResponseEntity {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
			ConstraintViolationException ex) {
		return super.prepareResponseMessage(null, true, ex.getMessage(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		return super.prepareResponseMessage(null, true, ErrorMessageEnum.INCORRECT_PARAMETERS.getValue(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<Map<String, Object>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
		return super.prepareResponseMessage(null, true, ErrorMessageEnum.CUSTOMER_NOT_FOUND.getValue(),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CreditLimitIsNotEnoughException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<Map<String, Object>> handleCreditLimitIsNotEnoughException(CreditLimitIsNotEnoughException ex) {
		return super.prepareResponseMessage(null, true, ErrorMessageEnum.CREDIT_LIMIT_IS_NOT_ENOUGH.getValue(),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UsableCreditAmountIsNotEnoughException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<Map<String, Object>> handleUsableCreditAmountIsNotEnoughException(UsableCreditAmountIsNotEnoughException ex) {
		return super.prepareResponseMessage(null, true, ErrorMessageEnum.USABLE_CREDIT_AMOUNT_IS_NOT_ENOUGH.getValue(),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(LoanNotFoundException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<Map<String, Object>> handleLoanNotFoundException(LoanNotFoundException ex) {
		return super.prepareResponseMessage(null, true, ErrorMessageEnum.LOAN_NOT_FOUND.getValue(),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(LoanHasBeenPaidException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<Map<String, Object>> handleLoanHasBeenPaidException(LoanHasBeenPaidException ex) {
		return super.prepareResponseMessage(null, true, ErrorMessageEnum.LOAN_HAS_ALREADY_BEEN_PAID.getValue(),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(AmountNotEnoughForInstallmentException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<Map<String, Object>> handleAmountNotEnoughForInstallmentException(AmountNotEnoughForInstallmentException ex) {
		return super.prepareResponseMessage(null, true, ErrorMessageEnum.AMOUNT_NOT_ENOUGH_FOR_INSTALLMENT.getValue(),
				HttpStatus.CONFLICT);
	}

}