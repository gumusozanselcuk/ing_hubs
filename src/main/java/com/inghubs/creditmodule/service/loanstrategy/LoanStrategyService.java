package com.inghubs.creditmodule.service.loanstrategy;

import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoanStrategyService {

    Map<String, LoanStrategy> loanStrategyMap;

    public LoanStrategyService(Map<String, LoanStrategy> loanStrategies) {
        this.loanStrategyMap = loanStrategies.values().stream()
                .collect(Collectors.toMap(LoanStrategy::getType, loanStrategy -> loanStrategy));
    }


    public Loan getLoanToBeCreatedByStrategy(LoanCreationRequestDTO loanCreationRequestDTO){
        return loanStrategyMap.get(loanCreationRequestDTO.getNumberOfRequestedInstallments().toString()).getLoanToBeCreatedByStrategy(loanCreationRequestDTO);
    }

    public List<LoanInstallment> getLoanInstallmentsOfLoanByStrategy(Loan loan){
        return loanStrategyMap.get(loan.getNumberOfInstallment().toString()).getLoanInstallmentsOfLoanByStrategy(loan);
    }
}
