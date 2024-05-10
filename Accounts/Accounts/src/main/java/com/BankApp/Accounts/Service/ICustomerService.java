package com.BankApp.Accounts.Service;

import com.BankApp.Accounts.Dto.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
