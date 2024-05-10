package com.BankApp.Accounts.Dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Have the details about customer , loan , account related information"
)
public class CustomerDetailsDto {

    @NotEmpty(message = "Enter a proper mail")
    private String name;


    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;


    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;


    @Schema(description = "Account related information")
    private AccountsDto accountsDto;


    @Schema(description = "Cards related information")
    private CardsDto cardsDto;


    @Schema(description = "Loans related information")
    private LoansDto loansDto;
}
