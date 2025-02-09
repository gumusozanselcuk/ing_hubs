package com.inghubs.creditmodule.repository;

import com.inghubs.creditmodule.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for Customer entity.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Retrieves a customer by id.
     *
     * @param customerId id of the customer
     * @return the customer
     */
    Optional<Customer> findCustomerById(Long customerId);

    /**
     * Updates a customer credit limit by id.
     *
     * @param customerId id of the customer
     * @param usedCreditLimit used credit limit
     */
    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.usedCreditLimit = c.usedCreditLimit + :usedCreditLimit WHERE c.id = :customerId")
    void updateCustomerUsedCreditLimitByCustomerId(Long customerId, Double usedCreditLimit);
}
