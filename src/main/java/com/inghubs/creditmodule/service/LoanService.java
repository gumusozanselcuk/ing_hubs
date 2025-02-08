package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.dto.LoanDTO;
import com.inghubs.creditmodule.entity.Customer;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.CreditLimitIsNotEnoughException;
import com.inghubs.creditmodule.exception.UsableCreditAmountIsNotEnoughException;
import com.inghubs.creditmodule.repository.LoanRepository;
import com.inghubs.creditmodule.service.loanstrategy.LoanStrategyService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final ModelMapper modelMapper;

    private final CustomerService customerService;

    private final LoanStrategyService loanStrategyService;

    private final LoanInstallmentService loanInstallmentService;

    public LoanService(LoanRepository loanRepository, ModelMapper modelMapper, CustomerService customerService, LoanStrategyService loanStrategyService, LoanInstallmentService loanInstallmentService) {
        this.modelMapper = modelMapper;
        this.loanRepository = loanRepository;
        this.customerService = customerService;
        this.loanStrategyService = loanStrategyService;
        this.loanInstallmentService = loanInstallmentService;
    }

    public List<LoanDTO> getCustomerLoans(Long customerId, int page, int size){
        Page<Loan> loans = loanRepository.findLoansByCustomerId(customerId,
                PageRequest.of(page,size));

        List<LoanDTO> loansResponse = loans.getContent().stream()
                .map(loan -> modelMapper.map(loan, LoanDTO.class))
                .collect(Collectors.toList());

        return loansResponse;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public LoanDTO createLoan(LoanCreationRequestDTO loanCreationRequestDTO){
        checkUserLimit(loanCreationRequestDTO.getCustomerId(), loanCreationRequestDTO.getRequestedLoanAmount());
        Loan loan = saveLoan(loanCreationRequestDTO);
        LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
        saveLoanInstallments(loan);
        return loanDTO;
    }

    private void checkUserLimit(Long customerId, Double requestedLoanAmount){
        Customer customer = customerService.getCustomerById(customerId);
        Double usableCreditLimit = customer.getCreditLimit()-customer.getUsedCreditLimit();

        if(customer.getCreditLimit()<requestedLoanAmount){
            throw new CreditLimitIsNotEnoughException(ErrorMessageEnum.CREDIT_LIMIT_IS_NOT_ENOUGH.getValue());
        }
        if(requestedLoanAmount>usableCreditLimit){
            throw new UsableCreditAmountIsNotEnoughException(ErrorMessageEnum.USABLE_CREDIT_AMOUNT_IS_NOT_ENOUGH.getValue());
        }
    }

    private Loan saveLoan(LoanCreationRequestDTO loanCreationRequestDTO){
        Loan loan = loanStrategyService.getLoanToBeCreatedByStrategy(loanCreationRequestDTO);
        loanRepository.save(loan);
        return loan;
    }

    private void saveLoanInstallments(Loan loan){
        List<LoanInstallment> loanInstallments =
                loanStrategyService.getLoanInstallmentsOfLoanByStrategy(loan);
        loanInstallmentService.saveLoanInstallments(loanInstallments);
    }
}
