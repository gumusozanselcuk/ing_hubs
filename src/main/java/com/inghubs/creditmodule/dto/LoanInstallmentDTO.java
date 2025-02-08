package com.inghubs.creditmodule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LoanInstallmentDTO {

    private Long id;

    private Long loanId;

    private Double amount;

    private Double paidAmount;

    private Date dueDate;

    private Date paymentDate;

    private Boolean isPaid;
}