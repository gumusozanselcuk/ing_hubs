package com.inghubs.creditmodule.service.loanstrategy;

import com.inghubs.creditmodule.dto.LoanCreationRequestDTO;
import com.inghubs.creditmodule.entity.Loan;
import com.inghubs.creditmodule.entity.LoanInstallment;
import com.inghubs.creditmodule.enums.InstallmentNumberEnum;
import com.inghubs.creditmodule.enums.InterestRateEnum;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TwentyFourMonthsLoanStrategyService implements LoanStrategy {

    private static final Integer INSTALLMENT_NUMBER = InstallmentNumberEnum.TWENTY_FOUR.getValue();

    private static final Double INTEREST_RATE = InterestRateEnum.ZERO_POINT_FIVE.getValue();

    @Override
    public Loan getLoanToBeCreatedByStrategy(LoanCreationRequestDTO loanCreationRequestDTO) {
        Loan loan = Loan.builder()
                .loanAmount(loanCreationRequestDTO.getRequestedLoanAmount())
                .createDate(new Date())
                .isPaid(false)
                .customerId(loanCreationRequestDTO.getCustomerId())
                .numberOfInstallment(INSTALLMENT_NUMBER)
                .build();
        return loan;
    }

    @Override
    public List<LoanInstallment> getLoanInstallmentsOfLoanByStrategy(Loan loan) {
        List<LoanInstallment> loanInstallments = new ArrayList<>();
        Double installmentAmount = (loan.getLoanAmount()*(1+INTEREST_RATE)) / loan.getNumberOfInstallment();
        BigDecimal result = new BigDecimal(installmentAmount).setScale(2, RoundingMode.HALF_UP);
        installmentAmount = result.doubleValue();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(loan.getCreateDate());
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        for (int i = 0; i < loan.getNumberOfInstallment(); i++) {
            Date dueDate = calendar.getTime();
            LoanInstallment installment = new LoanInstallment(loan.getId(), installmentAmount, 0.0, dueDate, null, false, loan);
            loanInstallments.add(installment);

            calendar.add(Calendar.MONTH, 1);
        }

        return loanInstallments;
    }

    @Override
    public String getType() {
        return INSTALLMENT_NUMBER.toString();
    }
}
