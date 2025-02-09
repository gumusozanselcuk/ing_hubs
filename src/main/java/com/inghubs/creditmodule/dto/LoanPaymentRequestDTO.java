package com.inghubs.creditmodule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for loan payment request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LoanPaymentRequestDTO {

    /**
     * Id of the customer for loan payment
     */
    @NotNull
    private Long customerId;

    /**
     * Id of the laon for loan payment
     */
    @NotNull
    private Long loanId;

    /**
     * Amount for loan payment
     */
    @NotNull
    private Double amountToBePaid;

}