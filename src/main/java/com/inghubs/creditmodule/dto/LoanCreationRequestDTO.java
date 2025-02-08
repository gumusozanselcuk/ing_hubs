package com.inghubs.creditmodule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCreationRequestDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Integer numberOfRequestedInstallments;

    @NotNull
    private Double requestedLoanAmount;

}
