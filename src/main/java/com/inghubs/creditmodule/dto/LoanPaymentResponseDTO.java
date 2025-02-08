package com.inghubs.creditmodule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LoanPaymentResponseDTO {

    private Long loanId;
    private Double paidAmount;
    private Double remainingAmount;
    private Integer paidInstallmentCount;
    private Boolean isAllLoanPaid;

}