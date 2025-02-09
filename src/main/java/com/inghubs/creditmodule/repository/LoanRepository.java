package com.inghubs.creditmodule.repository;

import com.inghubs.creditmodule.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findLoansByCustomerId(Long customerId, Pageable pageable);

    Optional<Loan> findByIdAndCustomerId(Long id, Long customerId);
}
