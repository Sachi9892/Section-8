package com.BankApp.Accounts.Dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Have the details related to account"
)
public class AccountsDto {

    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    @Schema(
            name = "Account no",
            description = "Have the details about account number",
            example = "7124001000"
    )
    private Long accountNumber;


    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema(
            name = "Branch Address",
            description = "Have the details about branch address",
            example = "Vile parle west"
    )
    private String branchAddress;
}
