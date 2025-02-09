package com.inghubs.creditmodule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Loan entity represents a loan in the credit module.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "loans")
public class Loan implements Serializable {

    private static final long serialVersionUID = 7201333590171773470L;

    /**
     * Unique identifier for the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Customer id of the loan
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * Amount of the loan
     */
    @Column(name = "loan_amount")
    private Double loanAmount;

    /**
     * Installment number of the loan
     */
    @Column(name = "number_of_installment")
    private Integer numberOfInstallment;

    /**
     * Create date of the loan
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * Paid info of the loan
     */
    @Column(name = "is_paid")
    private Boolean isPaid;

    /**
     * Installments of the loan
     */
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LoanInstallment> installments;
}
