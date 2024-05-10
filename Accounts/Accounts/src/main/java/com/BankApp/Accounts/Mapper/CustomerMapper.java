package com.BankApp.Accounts.Mapper;

import com.BankApp.Accounts.Dto.CustomerDetailsDto;
import com.BankApp.Accounts.Entity.Customer;

public class CustomerMapper {

    public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {

        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());
        return customerDetailsDto;

    }
}
