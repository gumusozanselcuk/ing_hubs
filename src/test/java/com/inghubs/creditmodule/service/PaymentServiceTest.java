package com.inghubs.creditmodule.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import com.inghubs.creditmodule.dto.LoanPaymentResponseDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.AmountNotEnoughForInstallmentException;
import com.inghubs.creditmodule.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PaymentServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPayInstallments_Success() {
        Loan mockLoan = getMockLoanWithTwoInstallments();

        Double amountToBePaid = 250.0;
        LoanPaymentResponseDTO result = paymentService.payInstallments(amountToBePaid, mockLoan);

        assertNotEquals(0.0, result.getPaidAmount());
        assertEquals(2, result.getPaidInstallmentCount());
        assertTrue(result.getIsAllLoanPaid());
        verify(loanRepository, times(1)).save(mockLoan);
    }

    @Test
    public void testPayInstallments_PartialPayment() {
        Loan mockLoan = getMockLoanWithTwoInstallments();

        Double amountToBePaid = 100.0;

        LoanPaymentResponseDTO result = paymentService.payInstallments(amountToBePaid, mockLoan);

        assertNotEquals(0.0, result.getPaidAmount());
        assertEquals(1, result.getPaidInstallmentCount());
        assertFalse(result.getIsAllLoanPaid());
        verify(loanRepository, times(1)).save(mockLoan);
        verify(customerService, never()).updateCustomerUsedCreditLimitByCustomerId(anyLong(), anyDouble());
    }

    @Test
    public void testPayInstallments_AmountNotEnough() {
        Loan mockLoan = new Loan();
        LoanInstallment mockInstallment = new LoanInstallment();
        mockInstallment.setAmount(100.0);
        mockInstallment.setDueDate(new Date());
        mockInstallment.setIsPaid(false);

        mockLoan.setInstallments(Collections.singletonList(mockInstallment));

        Double amountToBePaid = 50.0;

        AmountNotEnoughForInstallmentException exception = assertThrows(AmountNotEnoughForInstallmentException.class, () -> {
            paymentService.payInstallments(amountToBePaid, mockLoan);
        });
        assertEquals(ErrorMessageEnum.AMOUNT_NOT_ENOUGH_FOR_INSTALLMENT.getValue(), exception.getMessage());
    }

    private static Loan getMockLoanWithTwoInstallments() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();

        Loan mockLoan = new Loan();
        LoanInstallment mockInstallment1 = new LoanInstallment();
        mockInstallment1.setAmount(100.0);
        mockInstallment1.setDueDate(date);
        mockInstallment1.setIsPaid(false);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();

        LoanInstallment mockInstallment2 = new LoanInstallment();
        mockInstallment2.setAmount(150.0);
        mockInstallment2.setDueDate(date);
        mockInstallment2.setIsPaid(false);

        mockLoan.setInstallments(Arrays.asList(mockInstallment1, mockInstallment2));
        return mockLoan;
    }
}