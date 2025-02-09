package com.inghubs.creditmodule.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Collections;

import com.inghubs.creditmodule.dto.LoanInstallmentDTO;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.LoanNotFoundException;
import com.inghubs.creditmodule.repository.LoanInstallmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class LoanInstallmentServiceTest {

    @Mock
    private LoanInstallmentRepository loanInstallmentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LoanInstallmentService loanInstallmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLoanInstallments_LoanInstallmentsExist() {
        Long customerId = 1L;
        Long loanId = 1L;

        LoanInstallment mockLoanInstallment = new LoanInstallment();
        LoanInstallmentDTO mockLoanInstallmentDTO = new LoanInstallmentDTO();
        when(loanInstallmentRepository.findLoanInstallmentsByCustomerIdAndLoanId(customerId, loanId))
                .thenReturn(List.of(mockLoanInstallment));
        when(modelMapper.map(mockLoanInstallment, LoanInstallmentDTO.class)).thenReturn(mockLoanInstallmentDTO);

        List<LoanInstallmentDTO> result = loanInstallmentService.getLoanInstallments(customerId, loanId);

        assertEquals(1, result.size());
        assertEquals(mockLoanInstallmentDTO, result.get(0));
    }

    @Test
    public void testGetLoanInstallments_LoanInstallmentsDoNotExist() {
        Long customerId = 1L;
        Long loanId = 1L;

        when(loanInstallmentRepository.findLoanInstallmentsByCustomerIdAndLoanId(customerId, loanId))
                .thenReturn(Collections.emptyList());

        LoanNotFoundException exception = assertThrows(LoanNotFoundException.class, () -> {
            loanInstallmentService.getLoanInstallments(customerId, loanId);
        });
        assertEquals(ErrorMessageEnum.LOAN_NOT_FOUND.getValue(), exception.getMessage());
    }

    @Test
    public void testSaveLoanInstallments() {
        LoanInstallment mockLoanInstallment = new LoanInstallment();
        List<LoanInstallment> loanInstallments = List.of(mockLoanInstallment);

        loanInstallmentService.saveLoanInstallments(loanInstallments);
        verify(loanInstallmentRepository, times(1)).saveAll(loanInstallments);
    }
}