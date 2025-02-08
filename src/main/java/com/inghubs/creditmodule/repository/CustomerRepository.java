package com.inghubs.creditmodule.repository;

import com.inghubs.creditmodule.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerById(Long customerId);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.usedCreditLimit = c.usedCreditLimit + :usedCreditLimit WHERE c.id = :customerId")
    void updateCustomerName(Long customerId, Double usedCreditLimit);
}
