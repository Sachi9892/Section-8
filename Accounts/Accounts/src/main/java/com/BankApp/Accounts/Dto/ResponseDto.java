package com.BankApp.Accounts.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Schema(
        name = "Response Body",
        description = "This is response page"
)
public class ResponseDto {

    private String statusCode;

    private String statusMsg;

}
