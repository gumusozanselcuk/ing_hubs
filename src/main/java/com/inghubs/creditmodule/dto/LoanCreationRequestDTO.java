package com.inghubs.creditmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanCreationRequestDTO {

    private Long customerId;

    private Integer numberOfRequestedInstallments;

    private Double requestedInterestRate;

    private Double requestedLoanAmount;

}
