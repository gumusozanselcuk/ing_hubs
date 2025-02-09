package com.inghubs.creditmodule.repository;

import com.inghubs.creditmodule.entity.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {

    @Query(value = """
                   SELECT i.*
                   FROM customers c, loans l, installments i 
                   WHERE l.id = :loan_id
                   AND l.customer_id = :customer_id 
                   AND l.customer_id = c.id 
                   AND i.loan_id = l.id
                   """,
            nativeQuery = true)
    List<LoanInstallment> findLoanInstallmentsByCustomerIdAndLoanId(@Param("customer_id") Long customerId,
                                                                    @Param("loan_id") Long loanId);

}
