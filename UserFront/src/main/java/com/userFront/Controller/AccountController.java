package com.userFront.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//023 class level mapping; any paths within class must be preceeded by path defined with the class defn
@RequestMapping("/account")
public class AccountController {
	
	@RequestMapping("/primaryAccount")
	public String primaryAccount() {
		
		return "primaryAccount";
		
	}
	
	@RequestMapping("/savingsAccoung")
	public String savingsAccount() {
		
		return "savingsAccount";
		
	}
}
