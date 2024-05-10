package com.BankApp.Accounts.Service.Impl;

import com.BankApp.Accounts.Client.CardsFeignClient;
import com.BankApp.Accounts.Client.LoansFeignClient;
import com.BankApp.Accounts.Dto.AccountsDto;
import com.BankApp.Accounts.Dto.CardsDto;
import com.BankApp.Accounts.Dto.CustomerDetailsDto;
import com.BankApp.Accounts.Dto.LoansDto;
import com.BankApp.Accounts.Entity.Accounts;
import com.BankApp.Accounts.Entity.Customer;
import com.BankApp.Accounts.Exceptions.ResourceNotFoundException;
import com.BankApp.Accounts.Mapper.CustomerMapper;
import com.BankApp.Accounts.Repository.AccountRepository;
import com.BankApp.Accounts.Repository.CustomerRepository;
import com.BankApp.Accounts.Service.ICustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService  {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private CardsFeignClient cardsFeignClient;

    private LoansFeignClient loansFeignClient;

    private ModelMapper modelMapper;


    /**
     * @param - mobile number
     * @return - Details of customer in form of customer dto
     */

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "Mobile number", mobileNumber));

        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() ->
                new ResourceNotFoundException("Account", "Customer - Id", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());

        AccountsDto accountsDto = modelMapper.map(accounts, AccountsDto.class);

        customerDetailsDto.setAccountsDto(accountsDto);

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);

        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);

        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
