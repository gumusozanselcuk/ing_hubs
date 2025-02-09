package com.inghubs.creditmodule.service.loanstrategy;

import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.dto.LoanDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;

import java.util.List;

/**
 * Service interface for loan strategy operations.
 */
public interface LoanStrategy {

    Loan getLoanToBeCreatedByStrategy(LoanCreationRequestDTO loanCreationRequestDTO);

    List<LoanInstallment> getLoanInstallmentsOfLoanByStrategy(Loan loan);

    String getType();
}
