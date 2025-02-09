package com.inghubs.creditmodule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Customer entity represents a customer in the credit module.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customers")
public class Customer implements Serializable {

    private static final long serialVersionUID = 8689821010635973458L;

    /**
     * Unique identifier for the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the customer.
     */
    @Column(name = "name")
    private String name;

    /**
     * Surname of the customer.
     */
    @Column(name = "surname")
    private String surname;

    /**
     * Credit limit of the customer.
     */
    @Column(name = "credit_limit")
    private Double creditLimit;

    /**
     * Used credit limit of the customer.
     */
    @Column(name = "used_credit_limit")
    private Double usedCreditLimit;

    /**
     * Loans of the customer.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    private List<Loan> loans;
}
