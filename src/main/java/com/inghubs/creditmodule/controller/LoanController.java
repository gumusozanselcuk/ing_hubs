package com.inghubs.creditmodule.controller;


import com.inghubs.creditmodule.dto.BaseResponseEntity;
import com.inghubs.creditmodule.dto.LoanDTO;
import com.inghubs.creditmodule.enums.MessageEnum;
import com.inghubs.creditmodule.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController extends BaseResponseEntity {

    private static Logger logger = LoggerFactory.getLogger(LoanController.class);

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Map<String, Object>> getCustomerLoans(@PathVariable Long customerId,
                                                                @RequestParam Map<String, String> params) {
        logger.info("GET Received - Getting customer loans with customer id: {}", customerId);
        List<LoanDTO> loans = loanService.getCustomerLoans(customerId, params);
        return super.prepareResponseMessage(loans, false,
                MessageEnum.CUSTOMER_LOANS_RETURNED_SUCCESSFULLY.getValue(), HttpStatus.OK);
    }
}
