package com.BankApp.Accounts.Service;

import com.BankApp.Accounts.Dto.CustomerDto;

import java.util.List;

public interface AccountService {

    //Create account
    void createAccount(CustomerDto customerDto);


    //Fetch customer
    CustomerDto fetchAccountDetails( String mobileNumber);


    //Update account
    boolean updateAccount(CustomerDto customerDto);


    //Delete account
    boolean deleteAccount(String mobileNumber);

}
