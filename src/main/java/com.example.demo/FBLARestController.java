package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
public class FBLARestController {
	
	@GetMapping("/getAccountInfo")
	public Map<String, String> getAccountInfo() {
		Map<String, String> response = new HashMap<>();
        response.put("name", FBLAService.getName());
        response.put("email", FBLAService.getEmail());
        response.put("password", FBLAService.getPassword());
        return response;
	}
    @GetMapping("/getName")
    public Map<String, String> getName() {
        Map<String, String> response = new HashMap<>();
        response.put("name", FBLAService.getName());
        return response;
    }
    @GetMapping("/getID")
    public Map<String, Long> getID() {
        Map<String, Long> response = new HashMap<>();
        response.put("id", FBLAService.getID());
        return response;
    }
    @GetMapping("/getBalance")
    public Map<String, Double> getBalance() {
        Map<String, Double> response = new HashMap<>();
        response.put("balance", FBLAService.getBalance());
        return response;
    }
    @GetMapping("/getEmail")
    public Map<String, String> getEmail() {
    	Map<String, String> response = new HashMap<>();
        response.put("email", FBLAService.getEmail());
        return response;
    }
    @GetMapping("/getPassword")
    public Map<String, String> getPassword() {
    	Map<String, String> response = new HashMap<>();
        response.put("password", FBLAService.getPassword());
        return response;
    }
    @GetMapping("/emailUnique")
    public boolean emailUnique(@RequestParam String email) {
        return FBLAService.emailUnique(email);
    }
    
    @GetMapping("/getAllTransactions")
    public ArrayList<String> getAllTransactions(){
    	ArrayList<String> ret = FBLAService.getAllTransactions(true);
    	return ret;
    }
    @GetMapping("/filteredTransactionsDate")
    public ArrayList<String> filteredTransactionsDate(@RequestParam String startDate, @RequestParam String endDate){
        return FBLAService.filteredTransactionsDate(startDate, endDate);
    }
    @GetMapping("/filteredTransactionsType")
    public ArrayList<String> filteredTransactionsType(@RequestParam String type){
        return FBLAService.filteredTransactionsType(type);
    }
    @GetMapping("/filteredTransactionsDeposits")
    public ArrayList<String> filteredTransactionsDeposits(){
        return FBLAService.filteredTransactionsDeposits();
    }
    @GetMapping("/filteredTransactionsWithdrawals")
    public ArrayList<String> filteredTransactionsWithdrawals(){
        return FBLAService.filteredTransactionsWithdrawals();
    }
    @GetMapping("/getReportTypeDep")
    public ArrayList<Double> getReportTypeDep(@RequestParam double startDate, @RequestParam double endDate){
    	return FBLAService.getReport(startDate, endDate, "types", "deposits");
    }
    @GetMapping("/getReportTypeWith")
    public ArrayList<Double> getReportTypeWith(@RequestParam double startDate, @RequestParam double endDate){
    	return FBLAService.getReport(startDate, endDate, "types", "withdrawals");
    }
    @GetMapping("/getReportAmountDep")
    public ArrayList<Double> getReportAmountDep(@RequestParam double startDate, @RequestParam double endDate){
    	return FBLAService.getReport(startDate, endDate, "amounts", "deposits");
    }
    @GetMapping("/getReportAmountWith")
    public ArrayList<Double> getReportAmountWith(@RequestParam double startDate, @RequestParam double endDate){
    	return FBLAService.getReport(startDate, endDate, "amounts", "withdrawals");
    }
    @GetMapping("/hasDepositsWithdrawals")
    public ArrayList<Boolean> hasDepositsWithdrawals(@RequestParam double startDate, @RequestParam double endDate){
    	return FBLAService.hasDepositsWithdrawals(startDate, endDate);
    }
	

	
	@PostMapping("/deleteTransaction")
	public boolean deleteTransaction(@RequestParam String date, @RequestParam String type, @RequestParam String amount, @RequestParam String description){
	    return FBLAService.deleteTransaction(date, type, amount, description);
	}
	
	@PostMapping("/updateTransaction")
	public String updateTransaction(@RequestParam String date, @RequestParam String type, 
	    @RequestParam String amount, @RequestParam String description, @RequestParam(defaultValue="") String newDate, @RequestParam(defaultValue="") String newType, 
	    @RequestParam(defaultValue="") String newAmount, @RequestParam(defaultValue="") String newDescription)
	{
		if(FBLAService.updateTransaction(date, type, amount, description, newDate, newType, newAmount, newDescription)) {
			return "transactionHistory";
		}
		else{
			return "transactionFailure";
		}
	    
	}
	
	
	@GetMapping("/helpDesk")
	public ArrayList<String> helpDesk(@RequestParam String question) {
		return FBLAService.helpDesk(question);
	}
	
	
	
	@Autowired
    private FBLA_EmailService emailService;

    @GetMapping("/verificationEmail")
    public ArrayList<Integer> verificationEmail(@RequestParam String email) {
    	ArrayList<Integer> code = emailService.verificationEmail(email);
        return code; //returns either an arraylist of 6 random digits or an arraylist with -1 for an error
    }
//  @PostMapping("/sendReportEmail")
//  public String sendEmail() {
//      emailService.sendEmail("report", null);
//      return "Email sent successfully!";
//  }

}
