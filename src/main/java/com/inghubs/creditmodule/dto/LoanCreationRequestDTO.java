package com.inghubs.creditmodule.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCreationRequestDTO {

    private Long customerId;

    private Integer numberOfRequestedInstallments;

    private Double requestedInterestRate;

    private Double requestedLoanAmount;

}
