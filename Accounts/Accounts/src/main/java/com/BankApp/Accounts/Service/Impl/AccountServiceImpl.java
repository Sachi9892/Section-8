package com.BankApp.Accounts.Service.Impl;

import com.BankApp.Accounts.Constants.AccountsConstants;
import com.BankApp.Accounts.Dto.AccountsDto;
import com.BankApp.Accounts.Dto.CustomerDto;
import com.BankApp.Accounts.Entity.Accounts;
import com.BankApp.Accounts.Entity.Customer;
import com.BankApp.Accounts.Exceptions.CustomerAlreadyExitsException;
import com.BankApp.Accounts.Exceptions.ResourceNotFoundException;
import com.BankApp.Accounts.Repository.AccountRepository;
import com.BankApp.Accounts.Repository.CustomerRepository;
import com.BankApp.Accounts.Service.AccountService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private ModelMapper modelMapper;


    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);

        Optional<Customer> customerToBeCheck = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if(customerToBeCheck.isPresent()) {
            throw new CustomerAlreadyExitsException("Customer already exits with given mobile number : " + customer.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);

        accountRepository.save(createNewAccount(savedCustomer));
    }


    private Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();

        accounts.setCustomerId(customer.getCustomerId());
        long randomAccNo =  1000000000L + new Random().nextInt(900000000);

        accounts.setAccountNumber(randomAccNo);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);

        return accounts;

    }


    //Fetch account
    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer" , "Mobile Number" , mobileNumber));


        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() ->
                new ResourceNotFoundException("Account", "Customer id", mobileNumber));


        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        AccountsDto accountDto = modelMapper.map(accounts, AccountsDto.class);

        customerDto.setAccountsDto(accountDto);

        return customerDto;
    }


    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdate = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();

        if(accountsDto != null) {
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(()
                    -> new ResourceNotFoundException("Account", "Account Number", accountsDto.getAccountNumber().toString()));


            Accounts saved = accountRepository.save(accounts);

            Long customerId = saved.getCustomerId();

            Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer", "Customer - id", customerId.toString()));

            customerRepository.save(customer);

            isUpdate = true;

        }
        return isUpdate;
    }


    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "Customer-id", mobileNumber));

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }
}
