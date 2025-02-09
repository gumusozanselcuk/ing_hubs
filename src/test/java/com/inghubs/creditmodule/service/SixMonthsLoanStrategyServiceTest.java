package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.service.loanstrategy.SixMonthsLoanStrategyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SixMonthsLoanStrategyServiceTest {

    @InjectMocks
    private SixMonthsLoanStrategyService sixMonthsLoanStrategyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLoanToBeCreatedByStrategy() {
        LoanCreationRequestDTO loanCreationRequestDTO = new LoanCreationRequestDTO();
        loanCreationRequestDTO.setRequestedLoanAmount(1000.0);
        loanCreationRequestDTO.setCustomerId(12345L);

        Loan loan = sixMonthsLoanStrategyService.getLoanToBeCreatedByStrategy(loanCreationRequestDTO);

        assertNotNull(loan);
        assertEquals(loanCreationRequestDTO.getRequestedLoanAmount(), loan.getLoanAmount());
        assertNotNull(loan.getCreateDate());
        assertFalse(loan.getIsPaid());
        assertEquals(loanCreationRequestDTO.getCustomerId(), loan.getCustomerId());
        assertEquals(6, loan.getNumberOfInstallment());
    }

    @Test
    public void testGetLoanInstallmentsOfLoanByStrategy() {
        Loan loan = Loan.builder()
                .loanAmount(1000.0)
                .createDate(new Date())
                .isPaid(false)
                .customerId(12345L)
                .numberOfInstallment(6)
                .build();

        List<LoanInstallment> loanInstallments = sixMonthsLoanStrategyService.getLoanInstallmentsOfLoanByStrategy(loan);

        assertNotNull(loanInstallments);
        assertEquals(6, loanInstallments.size());

        Double installmentAmount = (1000.0 * 1.1) / 6;
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
        String type = sixMonthsLoanStrategyService.getType();
        assertEquals("6", type);
    }
}
