package com.tsv.implementation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TwoStepVerificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwoStepVerificationApplication.class, args);
	}

}
