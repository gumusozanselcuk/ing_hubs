package com.inghubs.creditmodule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "installments")
public class LoanInstallment implements Serializable {

    private static final long serialVersionUID = -4797875006766500616L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "is_paid")
    private Boolean isPaid;
}
