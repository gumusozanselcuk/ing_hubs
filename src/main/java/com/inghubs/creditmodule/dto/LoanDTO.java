package com.inghubs.creditmodule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Dto for loan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LoanDTO {

    /**
     * Id of the loan
     */
    private Long id;

    /**
     * Customer id of the loan
     */
    private Long customerId;

    /**
     * Amount of the loan
     */
    private Double loanAmount;

    /**
     * Installment number of the loan
     */
    private Integer numberOfInstallment;

    /**
     * Creation date of the loan
     */
    private Date createDate;

    /**
     * Paid info of the loan
     */
    private Boolean isPaid;
}