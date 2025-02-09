package com.inghubs.creditmodule.controller;


import com.inghubs.creditmodule.dto.*;
import com.inghubs.creditmodule.enums.MessageEnum;
import com.inghubs.creditmodule.service.LoanInstallmentService;
import com.inghubs.creditmodule.service.LoanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * Loan controller
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController extends BaseResponseEntity {

    private static Logger logger = LoggerFactory.getLogger(LoanController.class);

    /**
     * Loan service for loan operations
     */
    private final LoanService loanService;

    /**
     * Loan installment service for installment operations
     */
    private final LoanInstallmentService loanInstallmentService;

    /**
     * Constructor for LoanController
     *
     * @param loanService loan service
     * @param loanInstallmentService loan installment service
     */
    public LoanController(LoanService loanService, LoanInstallmentService loanInstallmentService) {
        this.loanService = loanService;
        this.loanInstallmentService = loanInstallmentService;
    }

    /**
     * Return loans of the given customer.
     *
     * @param customerId id of the customer
     * @param page page number for pagination
     * @param size size of the page
     * @return ResponseEntity that contains customer loans
     */
    @PreAuthorize("hasRole('ADMIN') or (#customerId == principal.customerId)")
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getCustomerLoans(
            @RequestParam @Min(1)  Long customerId,
            @RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(name = "size", defaultValue = "10") @Min(0) @Max(100)int size) {
        logger.info("GET Received - Getting customer loans with customer id: {}", customerId);
        List<LoanDTO> loans = loanService.getCustomerLoans(customerId, page, size);
        return super.prepareResponseMessage(loans, false,
                MessageEnum.CUSTOMER_LOANS_RETURNED_SUCCESSFULLY.getValue(), HttpStatus.OK);
    }

    /**
     * Return installments of the given loan.
     *
     * @param customerId id of the customer
     * @param loanId id of the loan
     * @return ResponseEntity that contains loan installments
     */
    @PreAuthorize("hasRole('ADMIN') or (#customerId == principal.customerId)")
    @GetMapping("/{loanId}")
    public ResponseEntity<Map<String, Object>> getLoanInstallments(
            @RequestParam @Min(1)  Long customerId,
            @PathVariable @Min(1) Long loanId) {
        logger.info("GET Received - Getting loan installments with loan id: {}", loanId);
        List<LoanInstallmentDTO> loans = loanInstallmentService.getLoanInstallments(customerId,loanId);
        return super.prepareResponseMessage(loans, false,
                MessageEnum.LOAN_INSTALLMENTS_RETURNED_SUCCESSFULLY.getValue(), HttpStatus.OK);
    }

    /**
     * Creates new loan.
     *
     * @param loanCreationRequestDTO dto contains params for loan creation
     * @return ResponseEntity that contains the created loan
     */
    @PreAuthorize("hasRole('ADMIN') or (#loanCreationRequestDTO.customerId == principal.customerId)")
    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createLoan(@RequestBody @Valid LoanCreationRequestDTO loanCreationRequestDTO) {
        logger.info("POST Received - Creating loan for customer id: {}", loanCreationRequestDTO.getCustomerId());
        LoanDTO loanDTO = loanService.createLoan(loanCreationRequestDTO);
        logger.info("POST Operation Done - Loan creation is done for customer id: {}", loanCreationRequestDTO.getCustomerId());
        return super.prepareResponseMessage(loanDTO, false,
                MessageEnum.LOAN_CREATED_SUCCESSFULLY.getValue(), HttpStatus.OK);
    }

    /**
     * Pays for the given loan.
     *
     * @param loanPaymentRequestDTO dto contains params for loan payment
     * @return ResponseEntity that contains the payment info
     */
    @PreAuthorize("hasRole('ADMIN') or (#loanPaymentRequestDTO.customerId == principal.customerId)")
    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> payLoan(@RequestBody @Valid LoanPaymentRequestDTO loanPaymentRequestDTO) {
        logger.info("POST Received - Paying loan for loan id: {}", loanPaymentRequestDTO.getLoanId());
        LoanPaymentResponseDTO loanPaymentResponseDTO = loanService.payLoan(loanPaymentRequestDTO);
        logger.info("POST Operation Done - Payment has been completed for loan id: {}", loanPaymentRequestDTO.getLoanId());
        return super.prepareResponseMessage(loanPaymentResponseDTO, false,
                MessageEnum.PAYMENT_DONE_SUCCESSFULLY.getValue(), HttpStatus.OK);
    }
}
