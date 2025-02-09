package com.inghubs.creditmodule.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.inghubs.creditmodule.entity.Customer;
import com.inghubs.creditmodule.enums.ErrorMessageEnum;
import com.inghubs.creditmodule.exception.CustomerNotFoundException;
import com.inghubs.creditmodule.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomerById_CustomerExists() {
        Long customerId = 1L;
        Customer mockCustomer = new Customer();
        when(customerRepository.findCustomerById(customerId)).thenReturn(Optional.of(mockCustomer));

        Customer result = customerService.getCustomerById(customerId);
        assertEquals(mockCustomer, result);
    }

    @Test
    public void testGetCustomerById_CustomerDoesNotExist() {
        Long customerId = 1L;
        when(customerRepository.findCustomerById(customerId)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(customerId);
        });
        assertEquals(ErrorMessageEnum.CUSTOMER_NOT_FOUND.getValue(), exception.getMessage());
    }

    @Test
    public void testUpdateCustomerUsedCreditLimitByCustomerId() {
        Long customerId = 1L;
        Double paidLoanAmount = 100.0;

        customerService.updateCustomerUsedCreditLimitByCustomerId(customerId, paidLoanAmount);
        verify(customerRepository).updateCustomerName(customerId, paidLoanAmount);
    }
}