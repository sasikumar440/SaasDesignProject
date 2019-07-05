package com.training.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	EmailService emailservice;
	

	@GetMapping("/Save")
	public String receiveEmail() throws Exception {
		emailservice.receiveEmail();
		return "Email attachment data stored in Database";	
	}
		 
	@PostMapping("/Approval")
	     String aknowledgement(@RequestBody String Emailid)  {
      try {
				emailservice.sendingEmail(Emailid);
	            return "Email Sent!";
          }catch(Exception ex) {
	            return "Error in sending email: "+ex;
	        }
	}
}
	 
