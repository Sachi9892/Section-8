package com.BankApp.Accounts;

import com.BankApp.Accounts.Dto.AccountContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Welcome to the sachin sonar's bank app Rest api section",
				description = "Bank - Service - App",
				version = "v1",
				contact = @Contact (
						name = "Sachin sonar" ,
						email = "Sachinandtech@gmail.com",
						url = "www.sachinandtech.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "www.apacheworld.org"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Sachin Bank App Rest Api Documenation"
		)
)
@EnableFeignClients

public class AccountsApplication {

	public static void main(String[] args) {

		SpringApplication.run(AccountsApplication.class, args);

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
