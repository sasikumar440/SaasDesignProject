package com.training;

/**
 * @author: sasikumar
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:db-config.properties")
public class AccountsPayable {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AccountsPayable.class, args);
		}
	}


