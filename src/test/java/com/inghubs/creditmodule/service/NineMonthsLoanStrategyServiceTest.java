package com.inghubs.creditmodule.service;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.service.loanstrategy.NineMonthsLoanStrategyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class NineMonthsLoanStrategyServiceTest {

    @InjectMocks
    private NineMonthsLoanStrategyService nineMonthsLoanStrategyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLoanToBeCreatedByStrategy() {
        LoanCreationRequestDTO loanCreationRequestDTO = new LoanCreationRequestDTO();
        loanCreationRequestDTO.setRequestedLoanAmount(1000.0);
        loanCreationRequestDTO.setCustomerId(12345L);

        Loan loan = nineMonthsLoanStrategyService.getLoanToBeCreatedByStrategy(loanCreationRequestDTO);

        assertNotNull(loan);
        assertEquals(loanCreationRequestDTO.getRequestedLoanAmount(), loan.getLoanAmount());
        assertNotNull(loan.getCreateDate());
        assertFalse(loan.getIsPaid());
        assertEquals(loanCreationRequestDTO.getCustomerId(), loan.getCustomerId());
        assertEquals(9, loan.getNumberOfInstallment());
    }

    @Test
    public void testGetLoanInstallmentsOfLoanByStrategy() {
        Loan loan = Loan.builder()
                .loanAmount(1000.0)
                .createDate(new Date())
                .isPaid(false)
                .customerId(12345L)
                .numberOfInstallment(9)
                .build();

        List<LoanInstallment> loanInstallments = nineMonthsLoanStrategyService.getLoanInstallmentsOfLoanByStrategy(loan);

        assertNotNull(loanInstallments);
        assertEquals(9, loanInstallments.size());

        Double installmentAmount = (1000.0 * 1.2) / 9;
        BigDecimal result = new BigDecimal(installmentAmount).setScale(2, RoundingMode.HALF_UP);
        installmentAmount = result.doubleValue();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(loan.getCreateDate());
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        for (int i = 0; i < loanInstallments.size(); i++) {
            LoanInstallment installment = loanInstallments.get(i);
            assertEquals(loan.getId(), installment.getLoanId());
            assertEquals(installmentAmount, installment.getAmount());
            assertEquals(0.0, installment.getPaidAmount());
            assertNotNull(installment.getDueDate());
            assertFalse(installment.getIsPaid());
            assertEquals(loan, installment.getLoan());

            calendar.add(Calendar.MONTH, 1);
        }
    }

    @Test
    public void testGetType() {
        String type = nineMonthsLoanStrategyService.getType();
        assertEquals("9", type);
    }
}