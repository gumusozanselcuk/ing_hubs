package com.inghubs.creditmodule.service.loanstrategy;

import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for handling loan strategy operations.
 */
@Service
public class LoanStrategyService {

    /**
     * Map that contains loan strategies
     */
    Map<String, LoanStrategy> loanStrategyMap;

    /**
     * Constructor for LoanStrategyService
     *
     * @param loanStrategies loan strategies
     */
    public LoanStrategyService(Map<String, LoanStrategy> loanStrategies) {
        this.loanStrategyMap = loanStrategies.values().stream()
                .collect(Collectors.toMap(LoanStrategy::getType, loanStrategy -> loanStrategy));
    }

    /**
     * Return loan by strategy
     *
     * @param loanCreationRequestDTO dto that contains info for creation loan
     * @return loan
     */
    public Loan getLoanToBeCreatedByStrategy(LoanCreationRequestDTO loanCreationRequestDTO){
        return loanStrategyMap.get(loanCreationRequestDTO.getNumberOfRequestedInstallments().toString()).getLoanToBeCreatedByStrategy(loanCreationRequestDTO);
    }

    /**
     * Return loan installments by strategy
     *
     * @param loan loan
     * @return list of loans
     */
    public List<LoanInstallment> getLoanInstallmentsOfLoanByStrategy(Loan loan){
        return loanStrategyMap.get(loan.getNumberOfInstallment().toString()).getLoanInstallmentsOfLoanByStrategy(loan);
    }
}
