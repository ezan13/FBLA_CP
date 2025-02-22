package com.example.demo;


import org.springframework.stereotype.Service;



import java.io.*;
import java.util.*;

@Service
public class FBLAService {
	/* Creates an account with a given name, balance, email, and password.
	 * Generates a unique ID for the account and stores the account information in AllAccounts.txt.
	 * Returns true if the account is successfully created, otherwise false.
	 */
    public static boolean createAccount(String name, double balance, String email, String password){
        long id = getNextID();
        String projectDir = System.getProperty("user.dir");
        String fileName = projectDir + "/files/" + id + ".txt";
        try{
        	if(!emailUnique(email)) {
        		return false;
        	}
            
            File file2 = new File(fileName);
            if(!(file2.exists())){
                file2.createNewFile();
            }
            FileWriter fr2 = new FileWriter(file2, true);
            PrintWriter pr2 = new PrintWriter(fr2);
            pr2.println("Starting Balance: " + balance);
            fr2.close();
            pr2.close();
            
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            FileWriter fr = new FileWriter(file, true);
            PrintWriter pr = new PrintWriter(fr);
            pr.println(id + "|" + name + "|" + balance + "|" + email + "|" + password);
            fr.close();
            pr.close();
            setIdToTop(id);
            return true;
        }
        catch(Exception e){
        	e.printStackTrace();
            return false;
        }
    }
    
    /* Checks if the given email is unique by comparing it with existing emails in AllAccounts.txt.
     * Returns true if the email is unique, otherwise false.
     */
    public static boolean emailUnique(String email) {
    	try {
    		String projectDir = System.getProperty("user.dir");
    		String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            
            Scanner sc = new Scanner(file);
            ArrayList<String> emails = new ArrayList<String>();
            while(sc.hasNextLine()) {
            	String line = sc.nextLine();
            	if(line.equals("No User Logged In")) {continue;}
            	String[] info = line.split("\\|");
            	emails.add(info[3]);
            }
            sc.close();
        	if(emails.contains(email)) {
        		return false;
        	}
        	return true;
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

    /* Retrieves the name of the currently logged-in account from AllAccounts.txt.
     * Returns the account name as a String.
     */
    public static String getName(){
        String[] info = getAccountInfo();
        String name = info[1];
        return name;
    }
    /* Retrieves the ID of the currently logged-in account from AllAccounts.txt.
     * Returns the account ID as a long.
     */
    public static long getID(){
        String[] info = getAccountInfo();
        long id = Long.parseLong(info[0]);
        return id;
    }
    /* Retrieves the balance of the currently logged-in account from AllAccounts.txt.
     * Returns the account balance as a Double.
     */
    public static Double getBalance(){
        String[] info = getAccountInfo();
        Double balance = Double.parseDouble(info[2]);
        return balance;
    }
    /* Retrieves the email of the currently logged-in account from AllAccounts.txt.
     * Returns the account email as a String.
     */
    public static String getEmail(){
        String[] info = getAccountInfo();
        String email = info[3];
        return email;
    }
    /* Retrieves the password of the currently logged-in account from AllAccounts.txt.
     * Returns the account password as a String.
     */
    public static String getPassword(){
        String[] info = getAccountInfo();
        String password = info[4];
        return password;
    }

    /* Adds a deposit to the balance of the currently logged-in account.
     * Updates the account balance in AllAccounts.txt and records the transaction in the account's transaction file.
     * Returns true if the deposit is successful, otherwise false.
     */
    public static boolean deposit(double deposit, String oldDate, String category, String description){
        //date format: month/date/year
    	String[] dateArray = oldDate.split("-");
    	String date = dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0];
        double balance = getBalance();
        if(deposit<=0){return false;}
        balance += deposit;
        long id = getID();
        updateBalance(balance);
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/" + id + ".txt";
            File f = new File(filePath);
            FileWriter fr = new FileWriter(f, true);
            PrintWriter pr = new PrintWriter(fr);
            pr.println(date + "|" + category + "|+" + deposit + "|" + description);
            fr.close();
            pr.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    /* Attempts to withdraw a given amount from the balance of the currently logged-in account.
     * Updates the account balance in AllAccounts.txt and records the transaction in the account's transaction file.
     * Returns true if the withdrawal is successful, otherwise false.
     */
    public static boolean withdrawal(double withdrawal, String oldDate, String category, String description){
    	String[] dateArray = oldDate.split("-");
    	String date = dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0];
        double balance = getBalance();
        if(withdrawal<=0 || withdrawal > balance){
            return false;
        }
        balance-=withdrawal;
        long id = getID();
        updateBalance(balance);
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/" + id + ".txt";
            File f = new File(filePath);
            FileWriter fr = new FileWriter(f, true);
            PrintWriter pr = new PrintWriter(fr);
            pr.println(date + "|" + category + "|-" + withdrawal + "|" + description);
            fr.close();
            pr.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    /* Retrieves all transactions for the currently logged-in account from the account's transaction file.
     * Returns an ArrayList of Strings containing the transactions.
     */
    public static ArrayList<String> getAllTransactions(boolean getStartingBalance){
        long id = getID();
        ArrayList<String> array = new ArrayList<String>();
        String projectDir = System.getProperty("user.dir");
        String fileName = projectDir + "/files/" + id + ".txt";
        try{
            File f = new File(fileName);
            Scanner sc = new Scanner(f);
            if(!getStartingBalance) {
            	sc.nextLine();//skip starting balance line
            }
            while(sc.hasNextLine()){
                array.add(sc.nextLine());
            }
            sc.close();
            array = sortTransactionsDate(array);
            return array;
        }
        catch(Exception e){
            return array;
        }
    }
    
    /* Retrieves the starting balance of the currently logged-in account from the account's transaction file.
     * Returns the starting balance as a String.
     */
    public static String getStartingBalance() {
    	long id = getID();
        String projectDir = System.getProperty("user.dir");
        String fileName = projectDir + "/files/" + id + ".txt";
        try{
            File f = new File(fileName);
            Scanner sc = new Scanner(f);
            String ret = sc.nextLine(); //skipping starting balance line
            sc.close();
            return ret;
        }
        catch(Exception e){
            return "Starting Balance could not be loaded";
        }
    }
    

	/* Updates a transaction in the account's transaction file with new details.
	 * Returns true if the update is successful, otherwise false.
	 */    	
    public static boolean updateTransaction(String date, String type, String amount, String description, String newDateUnformatted, String newType, String newAmount, String newDescription) {
        
        if (newType.equals("")) {
            newType = type;
        }

        String newAmountString = "";
        if (newAmount.equals("")) {
            newAmount = amount;
            newAmountString = amount;
        } else {
            double oldAmountDouble = Double.parseDouble(amount);
            double newAmountDouble = Double.parseDouble(newAmount);
            
            if (newAmountDouble > 0) {
                newAmountString = "+" + newAmountDouble;
            } else if (newAmountDouble < 0) {
                newAmountString = "" + newAmountDouble;
            } else {
                return false; 
            }

            double balance = getBalance();
//            if (balance - oldAmountDouble + newAmountDouble < 0) {    //returns false if negative balance after edit
//                return false; 
//            }
            balance -= oldAmountDouble;
            balance += newAmountDouble;
            updateBalance(balance);
        }

        String newDate = "";
        if (newDateUnformatted.equals("")) {
        	
            newDate = date; 
        } else {
            String[] dateArray = newDateUnformatted.split("-");
            newDate = dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0];
        }

        long id = getID(); 

        ArrayList<String> transactions = getAllTransactions(true);
        String oldTransaction = date + "|" + type + "|" + amount + "|" + description;
        String updatedTransaction = newDate + "|" + newType + "|" + newAmountString + "|" + newDescription;

        int index = transactions.indexOf(oldTransaction);
        if (index == -1) {
            return false;
        }

        transactions.set(index, updatedTransaction);

        try {
            String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/" + id + ".txt";
            File file = new File(filePath);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            
            for (String transaction : transactions) {
                pr.println(transaction); 
            }
            
            fr.close();
            pr.close();
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Generates the next unique account ID by finding the highest existing ID in AllAccounts.txt and incrementing it.
     * Returns the next available ID as a long.
     */
    public static long getNextID(){
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            long num = 1000;
            while(scanner.hasNextLine()){
            	String line = scanner.nextLine();
            	if(line.equals("No User Logged In")) {continue;}
                String[] info = line.split("\\|");
                if(num<Long.parseLong(info[0])){
                    num = Long.parseLong(info[0]);
                }
            }
            scanner.close();
            return num+1;
        }
        catch(Exception e){
            return 1001;
        }
    }

    /* Retrieves a list of all accounts from AllAccounts.txt.
     * Returns an ArrayList of Strings containing account information.
     */
    public static ArrayList<String> getAccountList(){
        ArrayList<String> array = new ArrayList<String>();
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
            	String line = scanner.nextLine();
            	if(line.equals("No User Logged In")) {continue;}
                array.add(line);
            }
            scanner.close();
            return array;
        }
        catch(Exception e){
            return array;
        }
    }

    /* Retrieves the information of the currently logged-in account from AllAccounts.txt.
     * Returns a String array containing the account's ID, name, balance, email, and password.
     */
    public static String[] getAccountInfo(){
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
        	if(line.equals("No User Logged In")) {
        		scanner.close();
        		return null;
        	}
            String[] info = line.split("\\|");
            scanner.close();
            return info;
        }
        catch(Exception e){
            return null;
        }
    }

    /* Updates the balance of the currently logged-in account in AllAccounts.txt.
     * Takes the new balance as a parameter and updates the account's balance in the file.
     */
    public static void updateBalance(double newBalance){
        ArrayList<String> accounts = getAccountList();
        String[] accountInfo = getAccountInfo();
        accountInfo[2] = newBalance+"";
        String change = accountInfo[0] + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
        accounts.set(0, change);
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String info: accounts){
                pr.println(info);
            }
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }
    

    /* Updates the name of the currently logged-in account in AllAccounts.txt.
     * Takes the new name as a parameter and updates the account's name in the file.
     */
    public static void updateName(String newName){
        ArrayList<String> accounts = getAccountList();
        String[] accountInfo = getAccountInfo();
        accountInfo[1] = newName;
        String change = accountInfo[0] + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
        accounts.set(0, change);
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String info: accounts){
                pr.println(info);
            }
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }

    /* Updates the email of the currently logged-in account in AllAccounts.txt.
     * Takes the new email as a parameter and updates the account's email in the file.
     */
    public static void updateEmail(String newEmail){
        ArrayList<String> accounts = getAccountList();
        String[] accountInfo = getAccountInfo();
        accountInfo[3] = newEmail;
        String change = accountInfo[0] + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
        accounts.set(0, change);
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String info: accounts){
                pr.println(info);
            }
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }

    /* Updates the password of the currently logged-in account in AllAccounts.txt.
     * Takes the new password as a parameter and updates the account's password in the file.
     */
    public static void updatePassword(String newPassword){
    	ArrayList<String> accounts = getAccountList();
        String[] accountInfo = getAccountInfo();
        accountInfo[4] = newPassword;
        String change = accountInfo[0] + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
        accounts.set(0, change);
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String info: accounts){
                pr.println(info);
            }
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }

    /* Deletes a transaction from the account's transaction file.
     * Returns true if the deletion is successful, otherwise false.
     */
    public static boolean deleteTransaction(String date, String type, String amt, String description){
        long id = getID();
        ArrayList<String> transactions =  getAllTransactions(true);
        String delete = date+"|"+type+"|"+amt+"|"+description;
        double amount = Double.parseDouble(amt);
        boolean ret = false;
        if(getBalance()-amount<0) {ret = true;}
        updateBalance(getBalance() - amount);
        transactions.remove(delete);
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/" + id + ".txt";
            File file = new File(filePath);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String transaction: transactions){
                pr.println(transaction);
            }
            fr.close();
            pr.close();
            return ret;
        }
        catch(Exception e){
            return false;
        }
    }
    
    /* Filters transactions for the currently logged-in account based on a specified date range.
     * Returns an ArrayList of Strings containing transactions within the specified date range.
     */
    public static ArrayList<String> filteredTransactionsDate(String oldStartDate, String oldEndDate){
    	String[] dateArray = oldStartDate.split("-");
    	String startDate = dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0];
    	String[] dateArray2 = oldEndDate.split("-");
    	String endDate = dateArray2[1] + "/" + dateArray2[2] + "/" + dateArray2[0];
        ArrayList<String> transactions = getAllTransactions(false);
        double startDay = dateToDouble(startDate);
        double endDay = dateToDouble(endDate);
        if(startDay > endDay){
            return null;
        }
        for(int i = transactions.size()-1; i>=0; i--){
            String transaction = transactions.get(i);
            String[] transactionInfo = transaction.split("\\|");
            double transactionDay = dateToDouble(transactionInfo[0]);
            if(startDay>transactionDay || endDay<transactionDay){
                transactions.remove(i);
            }
        }
        return transactions;
    }
    
    /* Filters transactions for the currently logged-in account based on a specified transaction type.
     * Returns an ArrayList of Strings containing transactions of the specified type.
     */
    public static ArrayList<String> filteredTransactionsType(String type){
        ArrayList<String> transactions = getAllTransactions(false);
        for(int i = transactions.size()-1; i>=0; i--){
            String transaction = transactions.get(i);
            String[] transactionInfo = transaction.split("\\|");
            if(!(transactionInfo[1].equals(type))){
                transactions.remove(i);
            }
        }
        return transactions;
    }
    
    /* Filters transactions for the currently logged-in account to include only deposits.
     * Returns an ArrayList of Strings containing deposit transactions.
     */
    public static ArrayList<String> filteredTransactionsDeposits(){
        ArrayList<String> transactions = getAllTransactions(false);
        for(int i = transactions.size()-1; i>=0; i--){
            String transaction = transactions.get(i);
            String[] transactionInfo = transaction.split("\\|");
            if(Double.parseDouble(transactionInfo[2])<0){
                transactions.remove(i);
            }
        }
        return transactions;
    }
    
    /* Filters transactions for the currently logged-in account to include only withdrawals.
     * Returns an ArrayList of Strings containing withdrawal transactions.
     */
    public static ArrayList<String> filteredTransactionsWithdrawals(){
        ArrayList<String> transactions = getAllTransactions(false);
        for(int i = transactions.size()-1; i>=0; i--){
            String transaction = transactions.get(i);
            String[] transactionInfo = transaction.split("\\|");
            if(Double.parseDouble(transactionInfo[2])>0){
                transactions.remove(i);
            }
        }
        return transactions;
    }

    /* Sorts an ArrayList of transactions by date in ascending order.
     * Returns an ArrayList of Strings containing the sorted transactions.
     */  
    public static ArrayList<String> sortTransactionsDate(ArrayList<String> transactions){
        ArrayList<Double> times = new ArrayList<Double>();
        for(String transaction: transactions){
            String[] transactionInfo = transaction.split("\\|");
            double transactionDate = dateToDouble(transactionInfo[0]);
            times.add(transactionDate);
        }
        Collections.sort(times);
        ArrayList<String> sorted = new ArrayList<String>();
        ArrayList<Double> usedTimes = new ArrayList<Double>();
        for(double time: times){
            if(usedTimes.contains(time)){continue;}
            for(String transaction: transactions){
                String[] transactionInfo = transaction.split("\\|");
                double transactionDate = dateToDouble(transactionInfo[0]);
                if(transactionDate == time){
                    sorted.add(transaction);
                }
            }
            usedTimes.add(time);
        }
        return sorted;
    }
    
    /* Converts a date string in the format "month/day/year" to a double representing the number of days since the year 0.
     * Returns the date as a double.
     */
    public static double dateToDouble(String date){
        //index 0 is month, index 1 is day, index 2 is year
        String[] array = date.split("/");
        double days = Double.parseDouble(array[2])*365.25 + (Double.parseDouble(array[0])/12)*365.25 + Double.parseDouble(array[1]);
        return days;
    }

	/* Provides a list of relevant web pages or actions based on a user's help desk search query.
	 * Returns an ArrayList of Strings containing suggested responses.
	 */
    public static ArrayList<String> helpDesk(String question){
    	question.replaceAll("[.?!/\\-]", "");
        String[] words = question.split(" ");
        ArrayList<String> responses = new ArrayList<String>();
        for(String word: words){
        	word = word.toLowerCase();
            if((word.equals("add") || word.equals("deposit")) && !responses.contains("deposit")){
                responses.add("deposit");
            }
            if((word.equals("withdraw") || word.equals("take") || word.equals("withdrawal")) && !responses.contains("withdrawal")){
                responses.add("withdrawal");
            }
            if((word.equals("transaction") || word.equals("transactions") || word.equals("history") || word.equals("past") || 
            		word.equals("find") || word.equals("withdrawal") || word.equals("withdraw") || word.equals("deposit") || word.equals("view") || word.equals("previous")) && !responses.contains("transactionHistory")){
            	responses.add("transactionHistory");
            }
            if((word.equals("balance") || word.equals("current") || word.equals("info") || word.equals("account") || word.equals("information")
            		|| word.equals("email") || word.equals("name") || word.equals("change") || word.equals("password") || word.equals("id")) && !responses.contains("accountInfo")){
            	responses.add("accountInfo");
            }
            if(word.equals("report") || word.equals("reports") || word.equals("week") || word.equals("weekly") || word.equals("month") || word.equals("monthly")) {
            	responses.add("reports");
            }
        }
        responses.add("home");
        return responses;
    }

    /* Validates an account by checking the provided ID and password against the information in AllAccounts.txt.
     * Returns true if the account is validated, otherwise false.
     */
    public static boolean validate(long id, String password){
        try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
            	if(line.equals("No User Logged In")) {continue;}
                String[] info = line.split("\\|");
                
                long accID = Long.parseLong(info[0]);
                String accPW = info[4];
                
                if(id == accID && password.equals(accPW)){
                    sc.close();
                    setIdToTop(id);
                    return true;
                }
            }
            sc.close();
            return false;
        }
        catch(Exception e){
        	e.printStackTrace();
            return false;
        }
    }
	 
    /* Moves the account with the specified ID to the top of AllAccounts.txt, indicating it is the currently logged-in account.
     */
    public static void setIdToTop(long id){
        ArrayList<String> array = new ArrayList<String>();
        try{
        	
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.equals("No User Logged In")) {continue;}
                String[] info = line.split("\\|");
                long accID = Long.parseLong(info[0]);
                if(id==accID){
                    array.add(0, line);
                }
                else{
                    array.add(line);
                }
            }
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(int i = 0; i<array.size(); i++) {
            	pr.println((array.get(i)));
            }
            scanner.close();
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }
    
    /* Logs out the currently logged-in account by resetting the top line of AllAccounts.txt to "No User Logged In".
     */
    public static void logOut() {
        ArrayList<String> array = new ArrayList<String>();
		try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
            	array.add(scanner.nextLine());
            }
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            pr.println("No User Logged In");
            for(int i = 0; i<array.size(); i++) {
            	pr.println((array.get(i)));
            }
            scanner.close();
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }
    
    /* Checks if a user is currently logged in by examining the top line of AllAccounts.txt.
     * Returns true if a user is logged in, otherwise false.
     */
    public static boolean isLoggedIn() {
    	try{
        	String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/AllAccounts.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            if(scanner.nextLine().equals("No User Logged In")) {
            	scanner.close();
            	return false;
            }
            scanner.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    /* Generates a report of transactions for the currently logged-in account within a specified date range.
     * Returns an ArrayList of Doubles containing transaction data based on the report type and deposit/withdrawal filter.
     */
    public static ArrayList<Double> getReport(double startDate, double endDate, String reportType, String depOrWith){
    	long id = getID();
    	try {
    		ArrayList<String> weekDeposits = new ArrayList<String>();
    		ArrayList<String> weekWithdrawals = new ArrayList<String>();
    		String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/" + id + ".txt";
            File f = new File(filePath);
            ArrayList<String> allTransactions = new ArrayList<String>();
            Scanner scanner = new Scanner(f);
            scanner.nextLine(); //skipping the starting balance
            while(scanner.hasNextLine()){
            	allTransactions.add(scanner.nextLine());
            }
            scanner.close();
            double numWith=0;
            double numDep=0;
            if(startDate>endDate) {
            	ArrayList<Double> error = new ArrayList<Double>();
            	error.add(-1.0);
            	return error;
            	//returns arraylist with -1 if error
            }
            for(String transaction: allTransactions) {
            	String[] transactionInfo = transaction.split("\\|");
            	double dateOfTransaction = dateToDouble(transactionInfo[0]);
            	if(dateOfTransaction>=startDate && endDate>=dateOfTransaction) {
            		if(Double.parseDouble(transactionInfo[2]) <0) {
            			weekWithdrawals.add(transaction);
            			numWith++;
            		}
            		else {
            			weekDeposits.add(transaction);
            			numDep++;
            		}
            	}
            }
            
            // 0: income, 1: friends, 2: other
            double[] depositTypesSpent = new double[3];
            // 0: food, 1: gas, 2: rent, 3: friends, 4: other
            double[] withdrawalTypesSpent = new double[5];
            
//            double totalDep = 0;
//            double totalWith = 0;
            
            double depIncome = 0;
            double depFriends = 0;
            double depOther = 0; 
            
            double withFood = 0;
            double withGas = 0;
            double withRent = 0;
            double withFriends = 0;
            double withOther = 0;
            
            for(String deposit: weekDeposits) {
            	String[] depositInfo = deposit.split("\\|");
            	double depositAmount = Double.parseDouble(depositInfo[2]);
            	String depositType = depositInfo[1];
            	if(depositType.equals("income")) {
            		depositTypesSpent[0] += depositAmount;
            		depIncome++;
            	}
            	else if(depositType.equals("friends")) {
            		depositTypesSpent[1] += depositAmount;
            		depFriends++;
            	}
            	else if(depositType.equals("other")) {
            		depositTypesSpent[2] += depositAmount;
            		depOther++;
            	}
//            	totalDep += depositAmount;
            }
            
            for(String withdrawal: weekWithdrawals) {
            	String[] withdrawalInfo = withdrawal.split("\\|");
            	double withdrawalAmount = Double.parseDouble(withdrawalInfo[2]);
            	String withdrawalType = withdrawalInfo[1];
            	if(withdrawalType.equals("food")) {
            		withdrawalTypesSpent[0] += withdrawalAmount;
            		withFood++;
            	}
            	else if(withdrawalType.equals("gas")) {
            		withdrawalTypesSpent[1] += withdrawalAmount;
            		withGas++;
            	}
            	else if(withdrawalType.equals("rent")) {
            		withdrawalTypesSpent[2] += withdrawalAmount;
            		withRent++;
            	}
            	else if(withdrawalType.equals("friends")) {
            		withdrawalTypesSpent[3] += withdrawalAmount;
            		withFriends++;
            	}
            	else if(withdrawalType.equals("other")) {
            		withdrawalTypesSpent[4] += withdrawalAmount;
            		withOther++;
            	}
//            	totalWith += withdrawalAmount;
            }
            
            ArrayList<Double> ret = new ArrayList<Double>();
            if(numDep==0 && numWith == 0) {
            	ret.add(-2.);
            	return ret;
            }
            
            //ret.add(numDep);
            //ret.add(totalDep);
            //ret.add(numWith);
            //ret.add(totalWith);
            if(reportType.equals("types")) {
            	if(depOrWith.equals("deposits")) {
            		ret.add(depIncome);
                    ret.add(depFriends);
                    ret.add(depOther);
            	}
            	else if(depOrWith.equals("withdrawals")) {
	                ret.add(withFood);
	                ret.add(withGas);
	                ret.add(withRent);
	                ret.add(withFriends);
	                ret.add(withOther);
            	}
            }
            
            
            else if(reportType.equals("amounts")) {
            	if(depOrWith.equals("deposits")) {
		            ret.add(depositTypesSpent[0]);
		            ret.add(depositTypesSpent[1]);
		            ret.add(depositTypesSpent[2]);
            	}
            	else if(depOrWith.equals("withdrawals")) {
		            ret.add(withdrawalTypesSpent[0]);
		            ret.add(withdrawalTypesSpent[1]);
		            ret.add(withdrawalTypesSpent[2]);
		            ret.add(withdrawalTypesSpent[3]);
		            ret.add(withdrawalTypesSpent[4]);
            	}
            }
           
            return ret;
            
            
            /*return errors:
             * -1: start date after end date
             * -2: no transactions in time frame
            */
    	}
    	catch(Exception e) {
    		ArrayList<Double> error = new ArrayList<Double>();
        	error.add(-1.0);
        	return error;
    	}
    }
    
    /* Checks if there are any deposits or withdrawals for the currently logged-in account within a specified date range.
     * Returns an ArrayList of Booleans indicating the presence of deposits and withdrawals.
     */
    public static ArrayList<Boolean> hasDepositsWithdrawals(double startDate, double endDate){
    	long id = getID();
    	try {
    		ArrayList<String> weekDeposits = new ArrayList<String>();
    		ArrayList<String> weekWithdrawals = new ArrayList<String>();
    		String projectDir = System.getProperty("user.dir");
            String filePath = projectDir + "/files/" + id + ".txt";
            File f = new File(filePath);
            ArrayList<String> allTransactions = new ArrayList<String>();
            Scanner scanner = new Scanner(f);
            scanner.nextLine(); //skipping the starting balance
            while(scanner.hasNextLine()){
            	allTransactions.add(scanner.nextLine());
            }
            scanner.close();
            double numWith=0;
            double numDep=0;
            for(String transaction: allTransactions) {
            	String[] transactionInfo = transaction.split("\\|");
            	double dateOfTransaction = dateToDouble(transactionInfo[0]);
            	if(dateOfTransaction>=startDate && endDate>=dateOfTransaction) {
            		if(Double.parseDouble(transactionInfo[2]) <0) {
            			weekWithdrawals.add(transaction);
            			numWith++;
            		}
            		else {
            			weekDeposits.add(transaction);
            			numDep++;
            		}
            	}
            }
            ArrayList<Boolean> ret = new ArrayList<Boolean>();
            if(numDep>0) {
            	ret.add(true);
            }
            else {
            	ret.add(false);
            }
            if(numWith>0) {
            	ret.add(true);
            }
            else {
            	ret.add(false);
            }
            return ret;
    	}
    	catch(Exception e) {
    		ArrayList<Boolean> error = new ArrayList<Boolean>();
        	error.add(false);
        	error.add(false);
        	return error;
    	}
    }
}

