package com.BankApp.Accounts.Repository;

import com.BankApp.Accounts.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer , Long> {

    //Custom finder method
    Optional<Customer> findByMobileNumber(String mobileNumber);


}
