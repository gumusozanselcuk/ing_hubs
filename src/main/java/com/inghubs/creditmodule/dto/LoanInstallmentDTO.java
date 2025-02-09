package com.inghubs.creditmodule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Dto for the loan installment
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LoanInstallmentDTO {

    /**
     * Id of the loan installment
     */
    private Long id;

    /**
     * Id of the loan
     */
    private Long loanId;

    /**
     * Amoount of the loan installment
     */
    private Double amount;

    /**
     * Paid amount of the loan installment
     */
    private Double paidAmount;

    /**
     * Due date of the loan installment
     */
    private Date dueDate;

    /**
     * Payment date of the loan installment
     */
    private Date paymentDate;

    /**
     * Paid info of the loan installment
     */
    private Boolean isPaid;
}