package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.dto.LoanDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.repository.LoanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final ModelMapper modelMapper;

    public LoanService(LoanRepository loanRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.loanRepository = loanRepository;
    }

    public List<LoanDTO> getCustomerLoans(Long customerId, Map<String, String> params){
        Page<Loan> loans = loanRepository.findLoansByCustomerId(customerId,
                PageRequest.of(Integer.valueOf(params.get("page")), Integer.valueOf(params.get("size"))));

        List<LoanDTO> loansResponse = loans.getContent().stream()
                .map(loan -> modelMapper.map(loan, LoanDTO.class))
                .collect(Collectors.toList());

        return loansResponse;
    }
}
