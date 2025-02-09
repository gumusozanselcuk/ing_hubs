package com.inghubs.creditmodule.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.dto.LoanDTO;
import com.inghubs.creditmodule.dto.LoanPaymentRequestDTO;
import com.inghubs.creditmodule.dto.LoanPaymentResponseDTO;
import com.inghubs.creditmodule.entity.Customer;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.LoanHasBeenPaidException;
import com.inghubs.creditmodule.exception.LoanNotFoundException;
import com.inghubs.creditmodule.repository.LoanRepository;
import com.inghubs.creditmodule.service.loanstrategy.LoanStrategyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CustomerService customerService;

    @Mock
    private LoanStrategyService loanStrategyService;

    @Mock
    private LoanInstallmentService loanInstallmentService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomerLoans() {
        Long customerId = 1L;
        int page = 0;
        int size = 10;
        Loan mockLoan = new Loan();
        LoanDTO mockLoanDTO = new LoanDTO();
        Page<Loan> mockPage = new PageImpl<>(List.of(mockLoan));
        when(loanRepository.findLoansByCustomerId(customerId, PageRequest.of(page, size))).thenReturn(mockPage);
        when(modelMapper.map(mockLoan, LoanDTO.class)).thenReturn(mockLoanDTO);

        List<LoanDTO> result = loanService.getCustomerLoans(customerId, page, size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockLoanDTO, result.get(0));
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void testCreateLoan() {
        Customer customer = Customer.builder()
                .id(1l)
                .creditLimit(10000.0)
                .surname("gumus")
                .name("ozan")
                .usedCreditLimit(0.0)
                .build();

        LoanCreationRequestDTO loanCreationRequestDTO = new LoanCreationRequestDTO();
        loanCreationRequestDTO.setCustomerId(1L);
        loanCreationRequestDTO.setRequestedLoanAmount(1000.0);
        loanCreationRequestDTO.setNumberOfRequestedInstallments(6);

        Loan mockLoan = new Loan();
        LoanDTO mockLoanDTO = new LoanDTO();

        when(customerService.getCustomerById(loanCreationRequestDTO.getCustomerId())).thenReturn(customer);
        when(loanStrategyService.getLoanToBeCreatedByStrategy(loanCreationRequestDTO)).thenReturn(mockLoan);
        when(loanRepository.save(mockLoan)).thenReturn(mockLoan);
        when(modelMapper.map(mockLoan, LoanDTO.class)).thenReturn(mockLoanDTO);

        LoanDTO result = loanService.createLoan(loanCreationRequestDTO);

        assertNotNull(result);
        assertEquals(mockLoanDTO, result);
        verify(loanInstallmentService, times(1)).saveLoanInstallments(anyList());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void testPayLoan() {
        LoanPaymentRequestDTO loanPaymentRequestDTO = new LoanPaymentRequestDTO();
        loanPaymentRequestDTO.setLoanId(1L);
        loanPaymentRequestDTO.setCustomerId(1L);
        loanPaymentRequestDTO.setAmountToBePaid(100.0);

        Loan mockLoan = new Loan();
        mockLoan.setIsPaid(false);

        LoanPaymentResponseDTO mockLoanPaymentResponseDTO = new LoanPaymentResponseDTO();
        when(loanRepository.findByIdAndCustomerId(loanPaymentRequestDTO.getLoanId(), loanPaymentRequestDTO.getCustomerId()))
                .thenReturn(Optional.of(mockLoan));
        when(paymentService.payInstallments(loanPaymentRequestDTO.getAmountToBePaid(), mockLoan))
                .thenReturn(mockLoanPaymentResponseDTO);

        LoanPaymentResponseDTO result = loanService.payLoan(loanPaymentRequestDTO);

        assertNotNull(result);
        assertEquals(mockLoanPaymentResponseDTO, result);
    }

    @Test
    public void testPayLoan_LoanNotFound() {
        LoanPaymentRequestDTO loanPaymentRequestDTO = new LoanPaymentRequestDTO();
        loanPaymentRequestDTO.setLoanId(1L);
        loanPaymentRequestDTO.setCustomerId(1L);

        when(loanRepository.findByIdAndCustomerId(loanPaymentRequestDTO.getLoanId(), loanPaymentRequestDTO.getCustomerId()))
                .thenReturn(Optional.empty());

        LoanNotFoundException exception = assertThrows(LoanNotFoundException.class, () -> {
            loanService.payLoan(loanPaymentRequestDTO);
        });
        assertEquals(ErrorMessageEnum.LOAN_NOT_FOUND.getValue(), exception.getMessage());
    }

    @Test
    public void testPayLoan_LoanAlreadyPaid() {
        LoanPaymentRequestDTO loanPaymentRequestDTO = new LoanPaymentRequestDTO();
        loanPaymentRequestDTO.setLoanId(1L);
        loanPaymentRequestDTO.setCustomerId(1L);

        Loan mockLoan = new Loan();
        mockLoan.setIsPaid(true);

        when(loanRepository.findByIdAndCustomerId(loanPaymentRequestDTO.getLoanId(), loanPaymentRequestDTO.getCustomerId()))
                .thenReturn(Optional.of(mockLoan));

        LoanHasBeenPaidException exception = assertThrows(LoanHasBeenPaidException.class, () -> {
            loanService.payLoan(loanPaymentRequestDTO);
        });

        assertEquals(ErrorMessageEnum.LOAN_HAS_ALREADY_BEEN_PAID.getValue(), exception.getMessage());
    }
}