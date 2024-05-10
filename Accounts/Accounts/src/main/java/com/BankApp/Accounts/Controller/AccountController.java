package com.BankApp.Accounts.Controller;

import com.BankApp.Accounts.Constants.AccountsConstants;
import com.BankApp.Accounts.Dto.AccountContactInfoDto;
import com.BankApp.Accounts.Dto.CustomerDto;
import com.BankApp.Accounts.Dto.ErrorResponseDto;
import com.BankApp.Accounts.Dto.ResponseDto;
import com.BankApp.Accounts.Service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD Rest Api for bank - app",
        description = "Api's for create , update , delete and to fetch"
)
@RestController
@RequestMapping(value = "/api" , produces = MediaType.APPLICATION_JSON_VALUE)
@Validated @AllArgsConstructor @NoArgsConstructor
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountContactInfoDto accountContactInfoDto;

    @Autowired
    private Environment environment;


    //Create
    @Operation(
            summary = "Creates New Account",
            description = "Rest api will take customer dto as param"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201 , AccountsConstants.MESSAGE_201));

    }



    //Fetch
    @Operation(
            summary = "Fetch Account",
            description = "Rest api will take mobile number as param"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})") String mobileNumber) {

        CustomerDto customerDto = accountService.fetchAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }




    //Update
    @Operation(
            summary = "Update Account",
            description = "Rest api will take customer dto as param"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "500",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {

        boolean isUpdated = accountService.updateAccount(customerDto);

        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200 , AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417 , AccountsConstants.MESSAGE_417_UPDATE));
        }
    }




    //Delete
    @Operation(
            summary = "Deletes Account",
            description = "Rest api take mobile number as a param"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})") String mobileNumber) {

        boolean isDeleted = accountService.deleteAccount(mobileNumber);

        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200 , AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417 , AccountsConstants.MESSAGE_417_DELETE));
        }
    }


    @GetMapping("/java-info")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactDetails() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }

}
