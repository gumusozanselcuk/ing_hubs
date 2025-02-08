package com.inghubs.creditmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCreationRequestDTO {

    private Long customerId;

    private Integer numberOfRequestedInstallments;

    private Double requestedLoanAmount;

}
