package com.inghubs.creditmodule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Date;

@Data
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