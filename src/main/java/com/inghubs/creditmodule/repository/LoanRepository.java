package com.inghubs.creditmodule.repository;

import com.inghubs.creditmodule.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findLoansByCustomerId(Long customerId, Pageable pageable);
}
