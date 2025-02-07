package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.dto.LoanInstallmentDTO;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.repository.LoanInstallmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanInstallmentService {

    private final LoanInstallmentRepository loanInstallmentRepository;

    private final ModelMapper modelMapper;

    public LoanInstallmentService(LoanInstallmentRepository loanInstallmentRepository, ModelMapper modelMapper) {
        this.loanInstallmentRepository = loanInstallmentRepository;
        this.modelMapper = modelMapper;
    }

    public List<LoanInstallmentDTO> getLoanInstallments(Long loanId){
        List<LoanInstallment> loanInstallments = loanInstallmentRepository.findLoanInstallmentsByLoanId(loanId);

        List<LoanInstallmentDTO> loanInstallmentsResponse = loanInstallments.stream()
                .map(loan -> modelMapper.map(loan, LoanInstallmentDTO.class))
                .collect(Collectors.toList());

        return loanInstallmentsResponse;
    }

}
