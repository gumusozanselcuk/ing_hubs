package com.inghubs.creditmodule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for loan payment response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LoanPaymentResponseDTO {

    /**
     * Id of the loan payment
     */
    private Long loanId;

    /**
     * Amount of the loan payment
     */
    private Double paidAmount;

    /**
     * Remaining amount of the loan payment
     */
    private Double remainingAmount;

    /**
     * Paid installment count of the loan payment
     */
    private Integer paidInstallmentCount;

    /**
     * All loan paid info of the loan payment
     */
    private Boolean isAllLoanPaid;

}