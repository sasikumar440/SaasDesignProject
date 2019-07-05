/*******************************************************************************
 * Copyright (C) Altimetrik 2018. All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 ******************************************************************************/
package com.training.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	//Production closes swagger
		@Value("${swagger.enable}")
		private boolean enableSwagger;

	    @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.training.controller"))
	                .paths(PathSelectors.regex("/.*"))
	                .build().enable(enableSwagger)
	                .apiInfo(metaData());
	    }

		private ApiInfo metaData() {

	        return new ApiInfo(
	                "SAAS product for Accounts Payable ",
	                "Accounts Payable for Invoice",
	                "2.0",
	                "https://www.altimetrik.com/privacy-policy/",
	                new Contact("SasiKumar", "https://training.altimetrik.com", "sgudapati@altimetrik.com"),
	                "Apache License Version 2.0",
	                "https://www.apache.org/licenses/LICENSE-2.0",
	                Collections.emptyList());
	}


}
