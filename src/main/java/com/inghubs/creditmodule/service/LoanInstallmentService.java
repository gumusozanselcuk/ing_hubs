package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.dto.LoanInstallmentDTO;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.LoanNotFoundException;
import com.inghubs.creditmodule.repository.LoanInstallmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling loan installment operations.
 */
@Service
public class LoanInstallmentService {

    /**
     * LoanInstallmentRepository repository for db operations
     */
    private final LoanInstallmentRepository loanInstallmentRepository;

    /**
     * Model mapper for sto transformations
     */
    private final ModelMapper modelMapper;

    /**
     * Constructor for LoanInstallmentService
     *
     * @param loanInstallmentRepository loan installment repository
     * @param modelMapper model mapper
     */
    public LoanInstallmentService(LoanInstallmentRepository loanInstallmentRepository, ModelMapper modelMapper) {
        this.loanInstallmentRepository = loanInstallmentRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves installments for the loan
     *
     * @param customerId id of the customer
     * @param loanId id of the loan
     * @return list of loan installments
     */
    public List<LoanInstallmentDTO> getLoanInstallments(Long customerId,Long loanId){
        List<LoanInstallment> loanInstallments = loanInstallmentRepository.
                findLoanInstallmentsByCustomerIdAndLoanId(customerId,loanId);

        if(loanInstallments.size()==0)
            throw new LoanNotFoundException(ErrorMessageEnum.LOAN_NOT_FOUND.getValue());

        List<LoanInstallmentDTO> loanInstallmentsResponse = loanInstallments.stream()
                .map(loan -> modelMapper.map(loan, LoanInstallmentDTO.class))
                .collect(Collectors.toList());

        return loanInstallmentsResponse;
    }

    /**
     * Save given loan installments
     *
     * @param loanInstallments loan installments
     */
    public void saveLoanInstallments(List<LoanInstallment> loanInstallments){
        loanInstallmentRepository.saveAll(loanInstallments);
    }
}
