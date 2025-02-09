package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.dto.LoanDTO;
import com.inghubs.creditmodule.dto.LoanPaymentRequestDTO;
import com.inghubs.creditmodule.dto.LoanPaymentResponseDTO;
import com.inghubs.creditmodule.entity.Customer;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.*;
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

/**
 * Service class for handling loan operations.
 */
@Service
public class LoanService {

    /**
     * LoanRepository repository for db operations
     */
    private final LoanRepository loanRepository;

    /**
     * Model mapper for sto transformations
     */
    private final ModelMapper modelMapper;

    /**
     * Customer service for customer operations
     */
    private final CustomerService customerService;

    /**
     * Loan strategy service for loan strategy operations
     */
    private final LoanStrategyService loanStrategyService;

    /**
     * Loan installment service for installment operations
     */
    private final LoanInstallmentService loanInstallmentService;

    /**
     * Payment service for payment operations
     */
    private final PaymentService paymentService;

    /**
     * Constructor for LoanService
     *
     * @param loanRepository loan repository
     * @param modelMapper model mapper
     * @param customerService customer service
     * @param loanStrategyService loan strategy service
     * @param loanInstallmentService loan installment service
     * @param paymentService payment service
     */
    public LoanService(LoanRepository loanRepository, ModelMapper modelMapper, CustomerService customerService,
                       LoanStrategyService loanStrategyService, LoanInstallmentService loanInstallmentService,
                       PaymentService paymentService) {
        this.modelMapper = modelMapper;
        this.loanRepository = loanRepository;
        this.customerService = customerService;
        this.loanStrategyService = loanStrategyService;
        this.loanInstallmentService = loanInstallmentService;
        this.paymentService = paymentService;
    }

    /**
     * Retrieves loans of the customer
     *
     * @param customerId id of the customer
     * @param page page
     * @param size page size
     * @return list of loans
     */
    public List<LoanDTO> getCustomerLoans(Long customerId, int page, int size){
        Page<Loan> loans = loanRepository.findLoansByCustomerId(customerId,
                PageRequest.of(page,size));

        List<LoanDTO> loansResponse = loans.getContent().stream()
                .map(loan -> modelMapper.map(loan, LoanDTO.class))
                .collect(Collectors.toList());

        return loansResponse;
    }

    /**
     * Creates loan
     *
     * @param loanCreationRequestDTO dto that contains info for creation loan
     * @return loan dto
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public LoanDTO createLoan(LoanCreationRequestDTO loanCreationRequestDTO){
        checkUserLimit(loanCreationRequestDTO.getCustomerId(), loanCreationRequestDTO.getRequestedLoanAmount());
        Loan loan = saveLoan(loanCreationRequestDTO);
        LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
        saveLoanInstallments(loan);
        return loanDTO;
    }

    /**
     * Pays loan
     *
     * @param loanPaymentRequestDTO dto that contains info for paying loan
     * @return loan payment response dto
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public LoanPaymentResponseDTO payLoan(LoanPaymentRequestDTO loanPaymentRequestDTO){
        Loan loan = loanRepository.findByIdAndCustomerId(loanPaymentRequestDTO.getLoanId(), loanPaymentRequestDTO.getCustomerId())
                .orElseThrow(() -> new LoanNotFoundException(ErrorMessageEnum.LOAN_NOT_FOUND.getValue()));

        if(loan.getIsPaid())
            throw new LoanHasBeenPaidException(ErrorMessageEnum.LOAN_HAS_ALREADY_BEEN_PAID.getValue());

        LoanPaymentResponseDTO loanPaymentResponseDTO =
                paymentService.payInstallments(loanPaymentRequestDTO.getAmountToBePaid(),loan);
        return loanPaymentResponseDTO;
    }

    /**
     * Controls that user has enough limits for getting loan
     *
     * @param customerId id of the customer
     * @param requestedLoanAmount load amount that requested
     */
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

    /**
     * Saves loan
     *
     * @param loanCreationRequestDTO dto that contains info for creation loan
     */
    private Loan saveLoan(LoanCreationRequestDTO loanCreationRequestDTO){
        Loan loan = loanStrategyService.getLoanToBeCreatedByStrategy(loanCreationRequestDTO);
        loanRepository.save(loan);
        return loan;
    }

    /**
     * Saves installments of the loan
     *
     * @param loan loan
     */
    private void saveLoanInstallments(Loan loan){
        List<LoanInstallment> loanInstallments =
                loanStrategyService.getLoanInstallmentsOfLoanByStrategy(loan);
        loanInstallmentService.saveLoanInstallments(loanInstallments);
    }
}
