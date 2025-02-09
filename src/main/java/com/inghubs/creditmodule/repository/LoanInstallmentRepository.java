package com.inghubs.creditmodule.repository;

import com.inghubs.creditmodule.entity.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {

    List<LoanInstallment> findLoanInstallmentsByLoanId(Long loanId);
}
