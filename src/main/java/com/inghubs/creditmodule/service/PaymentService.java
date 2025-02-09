package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.dto.LoanPaymentResponseDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.AmountNotEnoughForInstallmentException;
import com.inghubs.creditmodule.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling loan operations.
 */
@Service
public class PaymentService {

    /**
     * Reward coefficient for prepayment
     */
    private static final Double REWARD_COEFFICIENT = 0.999;

    /**
     * Penalty coefficient for prepayment
     */
    private static final Double PENALTY_COEFFICIENT = 1.001;

    /**
     * Customer service for customer operations
     */
    private final CustomerService customerService;

    /**
     * LoanRepository repository for db operations
     */
    private final LoanRepository loanRepository;

    /**
     * Constructor for PaymentService
     *
     * @param customerService customer service
     * @param loanRepository loan repository
     */
    public PaymentService(CustomerService customerService, LoanRepository loanRepository) {
        this.customerService = customerService;
        this.loanRepository = loanRepository;
    }

    /**
     * Pays installments with controls
     *
     * @param amountToBePaid amount to be paid
     * @param loan loan
     * @return loan payment response dto that contains payment infos
     */
    public synchronized LoanPaymentResponseDTO payInstallments(Double amountToBePaid, Loan loan){
        List<LoanInstallment> installmentsToBePaid = loan.getInstallments().stream()
                .filter(ins -> !ins.getIsPaid())
                .sorted(Comparator.comparing(LoanInstallment::getDueDate))
                .collect(Collectors.toList());

        controlMoneyIsEnoughForAnyPayment(amountToBePaid, installmentsToBePaid);

        LoanPaymentResponseDTO loanPaymentResponseDTO = LoanPaymentResponseDTO.builder()
                .isAllLoanPaid(false)
                .paidAmount(0.0)
                .remainingAmount(amountToBePaid)
                .loanId(loan.getId())
                .build();
        doPaymentOperation(amountToBePaid, loan, installmentsToBePaid, loanPaymentResponseDTO);
        return loanPaymentResponseDTO;
    }

    /**
     * Controls if any payment can be done
     *
     * @param amountToBePaid amount to be paid
     * @param installmentsToBePaid installments that will be paid
     */
    private void controlMoneyIsEnoughForAnyPayment(Double amountToBePaid, List<LoanInstallment> installmentsToBePaid) {
        LoanInstallment firstUnpaidInstallment = installmentsToBePaid.stream()
                .findFirst()
                .orElse(null);
        Double lastAmountOfInstallment = getInstallmentAmountWithPenaltyOrReward(firstUnpaidInstallment);

        if(lastAmountOfInstallment> amountToBePaid)
            throw new AmountNotEnoughForInstallmentException(ErrorMessageEnum.AMOUNT_NOT_ENOUGH_FOR_INSTALLMENT.getValue());
    }

    /**
     * Do payment operations for the installments and updates loan, customer and loan installments tables
     *
     * @param amountToBePaid amount to be paid
     * @param loan loan
     * @param installmentsToBePaid installments that will be paid
     * @param loanPaymentResponseDTO loan payment response dto
     * @return loan payment response dto that contains payment infos
     */
    private synchronized LoanPaymentResponseDTO doPaymentOperation( Double amountToBePaid, Loan loan,
                                                                    List<LoanInstallment> installmentsToBePaid,
                                                                    LoanPaymentResponseDTO loanPaymentResponseDTO) {
        int paidInstallmentCount = 0;
        double remainingMoney = amountToBePaid;
        Date today = new Date();

        for(LoanInstallment loanInstallment : installmentsToBePaid){
            Double lastAmountOfInstallment = getInstallmentAmountWithPenaltyOrReward(loanInstallment);
            if(remainingMoney > lastAmountOfInstallment){
                updateInstallment(lastAmountOfInstallment,loanInstallment,today);
                remainingMoney = remainingMoney - lastAmountOfInstallment;
                paidInstallmentCount++;
            }
        }

        if(paidInstallmentCount == installmentsToBePaid.size()){
            loan.setIsPaid(true);
            loanPaymentResponseDTO.setIsAllLoanPaid(true);
            customerService.updateCustomerUsedCreditLimitByCustomerId(loan.getCustomerId(),loan.getLoanAmount());
        }
        loanRepository.save(loan);

        double paidAmount = new BigDecimal(amountToBePaid - remainingMoney).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        remainingMoney = new BigDecimal(remainingMoney).setScale(2, RoundingMode.HALF_UP).doubleValue();

        loanPaymentResponseDTO.setPaidAmount(paidAmount);
        loanPaymentResponseDTO.setRemainingAmount(remainingMoney);
        loanPaymentResponseDTO.setPaidInstallmentCount(paidInstallmentCount);
        return loanPaymentResponseDTO;

    }

    /**
     * Updates installment
     *
     * @param lastAmount calculated amount to be paid
     * @param loanInstallment loan installment
     * @param today today date
     */
    private void updateInstallment(Double lastAmount, LoanInstallment loanInstallment, Date today){
        loanInstallment.setPaymentDate(today);
        loanInstallment.setPaidAmount(lastAmount);
        loanInstallment.setIsPaid(true);
    }

    /**
     * Returns calculated amount with penalty or reward to be paid
     *
     * @param loanInstallment installment
     * @return calculated last amount
     */
    private Double getInstallmentAmountWithPenaltyOrReward(LoanInstallment loanInstallment) {
        long passedDays = getDaysBetweenDueDateAndNow(loanInstallment.getDueDate());
        Double lastAmountOfInstallment = loanInstallment.getAmount();
        if(passedDays>0)
            lastAmountOfInstallment = loanInstallment.getAmount()*Math.pow(PENALTY_COEFFICIENT, passedDays);
        if(passedDays<0)
            lastAmountOfInstallment = loanInstallment.getAmount()*Math.pow(REWARD_COEFFICIENT, Math.abs(passedDays));

        BigDecimal result = new BigDecimal(lastAmountOfInstallment).setScale(2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    /**
     * Get dates between requested date and due date for reward and penalty calculation
     *
     * @param dueDate due date
     */
    private long getDaysBetweenDueDateAndNow(Date dueDate) {
        LocalDate localDueDate = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();

        return ChronoUnit.DAYS.between(localDueDate, today);
    }
}
