package com.inghubs.creditmodule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "loans")
public class Loan implements Serializable {

    private static final long serialVersionUID = 7201333590171773470L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "number_of_installment")
    private Integer numberOfInstallment;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "loan_id")
    private List<LoanInstallment> installments;
}
