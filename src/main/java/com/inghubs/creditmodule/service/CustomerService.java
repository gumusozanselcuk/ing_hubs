package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.entity.Customer;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.CustomerNotFoundException;
import com.inghubs.creditmodule.repository.CustomerRepository;

import org.springframework.stereotype.Service;

/**
 * Service class for handling customer operations.
 */
@Service
public class CustomerService {

    /**
     * Customer repository for db operations
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor for CustomerService
     *
     * @param customerRepository customer repository
     */
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves customer by id
     *
     * @param customerId id of the customer
     * @return customer
     */
    public Customer getCustomerById(Long customerId){
        Customer customer = customerRepository.findCustomerById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageEnum.CUSTOMER_NOT_FOUND.getValue()));
        return customer;
    }

    /**
     * Retrieves customer by id
     *
     * @param customerId id of the customer
     * @param paidLoanAmount ampunt to be paid for the loan
     */
    public void updateCustomerUsedCreditLimitByCustomerId(Long customerId, Double paidLoanAmount){
        customerRepository.updateCustomerUsedCreditLimitByCustomerId(customerId, paidLoanAmount);
    }


}
