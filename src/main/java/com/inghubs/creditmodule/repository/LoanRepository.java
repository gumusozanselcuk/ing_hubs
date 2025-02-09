package com.inghubs.creditmodule.repository;

import com.inghubs.creditmodule.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Loan entity.
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    /**
     * Retrieves loans by customer id and pageable.
     *
     * @param customerId id of the customer
     * @param pageable pageable
     * @return loans
     */
    Page<Loan> findLoansByCustomerId(Long customerId, Pageable pageable);

    /**
     * Retrieves loan by id and customer id.
     *
     * @param id loan id
     * @param customerId id of the customer
     * @return loans
     */
    Optional<Loan> findByIdAndCustomerId(Long id, Long customerId);
}
