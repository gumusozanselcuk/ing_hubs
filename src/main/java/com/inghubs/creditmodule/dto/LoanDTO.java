package com.inghubs.creditmodule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LoanDTO {

    private Long id;

    private Long customerId;

    private Double loanAmount;

    private Integer numberOfInstallment;

    private Date createDate;

    private Boolean isPaid;
}