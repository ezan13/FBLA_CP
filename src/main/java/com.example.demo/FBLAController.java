package com.example.demo;

//import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;	

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FBLAController {
    
    @GetMapping("/login")
    public String login() {
        return "index";
    }
    
    @GetMapping("/createAccount")
    public String createAccountMapping() {
        return "createAccount";m
    }
    
    @GetMapping("/directions")
    public String directionsMapping() {
        return "directions";
    }
    
    @GetMapping("/accountInfo")
    public String accountInfoMapping() {
    	if(!(FBLAService.isLoggedIn())) {
    		return "index";
    	}
        return "accountInfo";
    }
    
    @GetMapping("/home")
    public String homeMapping() {
    	if(!(FBLAService.isLoggedIn())) {
    		return "index";
    	}
        return "home";
    }
    
    @GetMapping("/newTransaction")
    public String newTransactionMapping() {
    	if(!(FBLAService.isLoggedIn())) {
    		return "index";
    	}
        return "newTransaction";
    }
    
    @GetMapping("/transactionHistory")
    public String transactionHistoryMapping() {
    	if(!(FBLAService.isLoggedIn())) {
    		return "index";
    	}
        return "transactionHistory";
    }
    
    @GetMapping("/reports")
    public String reportMapping() {
    	if(!(FBLAService.isLoggedIn())) {
    		return "index";
    	}
        return "reports";
    }
    
    @GetMapping("/creationError")
    public String creatiorErrorMapping() {
        return "creationError";
    }
    
    @GetMapping("/transactionFailure")
    public String transactionFailureMapping() {
    	if(!(FBLAService.isLoggedIn())) {
    		return "index";
    	}
        return "transactionFailure";
    }
    
    @GetMapping("/transactionSuccess")
    public String transactionSuccessMapping() {
    	if(!(FBLAService.isLoggedIn())) {
    		return "index";
    	}
        return "transactionSuccess";
    }
    
    @GetMapping("/loginError")
    public String loginErrorMapping() {
        return "loginError";
    }
    
    @PostMapping("/validate")
    public String validate(@RequestParam String attemptID, @RequestParam String attemptPassword) {
    	long longAttemptID=0;
    	try {
    		longAttemptID = Long.parseLong(attemptID);
    	}
    	catch(NumberFormatException e){
    		return "loginError";
    	}		
    	
		  if(FBLAService.validate(longAttemptID, attemptPassword)) 
		  {
			  return "home";
		  } 
		  else{
			  return "loginError";
		  }
		 
    }
    @PostMapping("/createAccount")
    public String createAccount(@RequestParam String name, @RequestParam double balance, 
        @RequestParam String email, @RequestParam String password)
    {
        if(FBLAService.createAccount(name, balance, email, password)) 
		  {
			  return "accountInfo";
		  } 
		  else{
			  return "creationError";
		  }
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam double amount, @RequestParam String date, @RequestParam String type, @RequestParam(defaultValue="N/A") String description){
    	if(FBLAService.deposit(amount, date, type, description)) {
    		return "transactionSuccess";
    	}
    	else {
    		return "transactionFailure";
    	}
    }
    @PostMapping("/withdrawal")
    public String withdrawal(@RequestParam double amount, @RequestParam String date, @RequestParam String type, @RequestParam(defaultValue="N/A") String description){
        if(FBLAService.withdrawal(amount, date, type, description)) {
    		return "transactionSuccess";
    	}
    	else {
    		return "transactionFailure";
    	}
    }
    @PostMapping("/updateName")
    public String updateName(@RequestParam String newName){
        FBLAService.updateName(newName);
        return "accountInfo";
    }
    @PostMapping("/updateEmail")
    public String updateEmail(@RequestParam String newEmail){
        FBLAService.updateEmail(newEmail);
        return "accountInfo";
    }
    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam String newPassword){
        FBLAService.updatePassword(newPassword);
        return "accountInfo";
    }
    
    @PostMapping("/logOut")
    public String logOut() {
    	FBLAService.logOut();
    	return "index";
    }
    
    
    

}
