package com.inghubs.creditmodule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * Loan installment entity represents a loan installment in the credit module.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "installments")
public class LoanInstallment implements Serializable {

    private static final long serialVersionUID = -4797875006766500616L;

    /**
     * Unique identifier for the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Loan id of the loan installment
     */
    @Column(name = "loan_id")
    private Long loanId;

    /**
     * Loan of the loan installment
     */
    @ManyToOne
    @JoinColumn(name = "loan_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private Loan loan;

    /**
     * Amount of the loan installment
     */
    @Column(name = "amount")
    private Double amount;

    /**
     * Paid amount of the loan installment
     */
    @Column(name = "paid_amount")
    private Double paidAmount;

    /**
     * Due date of the loan installment
     */
    @Column(name = "due_date")
    private Date dueDate;

    /**
     * Payment date of the loan installment
     */
    @Column(name = "payment_date")
    private Date paymentDate;

    /**
     * Paid info of the loan installment
     */
    @Column(name = "is_paid")
    private Boolean isPaid;

    public LoanInstallment(Long loanId, Double amount, Double paidAmount, Date dueDate, Date paymentDate, Boolean isPaid, Loan loan) {
        this.loanId = loanId;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
        this.loan = loan;
    }
}
