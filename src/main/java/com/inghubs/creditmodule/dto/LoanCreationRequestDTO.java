package com.inghubs.creditmodule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for creation loan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCreationRequestDTO {

    /**
     * Id of the customer
     */
    @NotNull
    private Long customerId;

    /**
     * Number of the requested installments
     */
    @NotNull
    private Integer numberOfRequestedInstallments;

    /**
     * Amount of the request loan
     */
    @NotNull
    private Double requestedLoanAmount;

}
