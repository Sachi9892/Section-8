package com.Loans.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "loans")
@Getter
@Setter
public class LoanContactInfoDto {

    String message;

    Map<String, String> contactDetails;

    List<String> onCallSupport;

}
