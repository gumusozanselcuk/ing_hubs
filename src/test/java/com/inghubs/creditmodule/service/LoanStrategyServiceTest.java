package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.service.loanstrategy.LoanStrategy;
import com.inghubs.creditmodule.service.loanstrategy.LoanStrategyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanStrategyServiceTest {

    private LoanStrategyService loanStrategyService;
    private Map<String, LoanStrategy> loanStrategyMap;

    @BeforeEach
    public void setUp() {
        loanStrategyMap = new HashMap<>();
        LoanStrategy strategy1 = Mockito.mock(LoanStrategy.class);
        LoanStrategy strategy2 = Mockito.mock(LoanStrategy.class);

        when(strategy1.getType()).thenReturn("12");
        when(strategy2.getType()).thenReturn("24");

        loanStrategyMap.put("12", strategy1);
        loanStrategyMap.put("24", strategy2);

        loanStrategyService = new LoanStrategyService(loanStrategyMap);
    }

    @Test
    public void testGetLoanToBeCreatedByStrategy() {
        LoanCreationRequestDTO loanCreationRequestDTO = new LoanCreationRequestDTO();
        loanCreationRequestDTO.setNumberOfRequestedInstallments(12);

        Loan expectedLoan = new Loan();
        when(loanStrategyMap.get("12").getLoanToBeCreatedByStrategy(any(LoanCreationRequestDTO.class))).thenReturn(expectedLoan);

        Loan actualLoan = loanStrategyService.getLoanToBeCreatedByStrategy(loanCreationRequestDTO);

        assertEquals(expectedLoan, actualLoan);
    }

    @Test
    public void testGetLoanInstallmentsOfLoanByStrategy() {
        Loan loan = new Loan();
        loan.setNumberOfInstallment(24);

        List<LoanInstallment> expectedInstallments = List.of(new LoanInstallment(), new LoanInstallment());
        when(loanStrategyMap.get("24").getLoanInstallmentsOfLoanByStrategy(any(Loan.class))).thenReturn(expectedInstallments);

        List<LoanInstallment> actualInstallments = loanStrategyService.getLoanInstallmentsOfLoanByStrategy(loan);

        assertEquals(expectedInstallments, actualInstallments);
    }
}