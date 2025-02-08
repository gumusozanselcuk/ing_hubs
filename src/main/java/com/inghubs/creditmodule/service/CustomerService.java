package com.inghubs.creditmodule.service;

import com.inghubs.creditmodule.entity.Customer;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.CustomerNotFoundException;
import com.inghubs.creditmodule.repository.CustomerRepository;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(Long customerId){
        Customer customer = customerRepository.findCustomerById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException(ErrorMessageEnum.CUSTOMER_NOT_FOUND.getValue()));
        return customer;
    }

}
