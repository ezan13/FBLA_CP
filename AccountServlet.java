package fbla_cp_java.path;

//imports tools needed to access and write to files
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        // response.setContentType("text/html");
        // response.getWriter().println("<h1>Hello, World!</h1>");

        if(path.equals("/Account/getName")){
            response.getWriter().println(getName());
        }
        else if(path.equals("/Account/getID")){
            response.getWriter().println(getID());
        }
        else if(path.equals("/Account/getEmail")){
            response.getWriter().println(getEmail());
        }
        else if(path.equals("/Account/getPassword")){
            response.getWriter().println(getPassword());
        }
        else if(path.equals("/Account/getBalance")){
            response.getWriter().println(getBalance());
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ran");
        response.sendRedirect("home.html");
        
        // String path = request.getServletPath();
        // if(path.equals("/AccountServlet/validate")){
        //     long id = Long.parseLong(request.getParameter("attemptID"));
        //     String password = request.getParameter("attemptPassword");
        //     if(validate(id,password)){
        //         response.sendRedirect("/FBLA_CP/home.html");
        //     }
        // }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if(path.equals("/Account/updateName")){
            String newName = request.getParameter("newName"); 
            updateName(newName);
        }
        else if(path.equals("/Account/updateEmail")){
            String newEmail = request.getParameter("newEmail"); 
            updateEmail(newEmail);
        }
        else if(path.equals("/Account/deposit")){
            double amount = Double.parseDouble(request.getParameter("amount")); 
            String date = request.getParameter("date"); 
            String category = request.getParameter("category");
            if(deposit(amount, date, category)){

            }
            else{

            }
        }
        else if(path.equals("/Account/withdrawl")){
            double amount = Double.parseDouble(request.getParameter("amount")); 
            String date = request.getParameter("date"); 
            String category = request.getParameter("category");
            if(withdrawl(amount, date, category)){

            }
            else{
                
            }
        }
        else if(path.equals("/Account/updatePassword")){
            String newPassword = request.getParameter("newPassword"); 
            String oldPassword = request.getParameter("oldPassword"); 
            if(updatePassword(oldPassword, newPassword)){

            }
            else{

            }
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // String path = request.getServletPath();
    }
















    /* Creates an account with a given name, balance, email, and password 
     * Creats a unique ID for the account in which other methods are called with
    */
    // public static long createAccount(String name, double balance, String email, String password){
    //     long id = getNextID();
    //     String fileName = id + ".txt";
    //     try{
    //         File file = new File(fileName);
    //         if(!(file.exists())){
    //             file.createNewFile();
    //         }
    //         File file2 = new File("AllAccounts.txt");
    //         FileWriter fr2 = new FileWriter(file2, true);
    //         PrintWriter pr2 = new PrintWriter(fr2);
    //         pr2.println(id + "|" + name + "|" + balance + "|" + email + "|" + password);
    //         fr2.close();
    //         pr2.close();
    //         return id;
    //     }
    //     catch(Exception e){
    //         return 0;
    //     }
    // }
    public static void createAccount(String name, double balance, String email, String password){
        long id = getNextID();
        String fileName = id + ".txt";
        try{
            File file = new File(fileName);
            if(!(file.exists())){
                file.createNewFile();
            }
            File file2 = new File("AllAccounts.txt");
            FileWriter fr2 = new FileWriter(file2, true);
            PrintWriter pr2 = new PrintWriter(fr2);
            pr2.println(id + "|" + name + "|" + balance + "|" + email + "|" + password);
            fr2.close();
            pr2.close();
            setIdToTop(id);
        }
        catch(Exception e){
            return;
        }
    }

    // Creates accessor methods for an account's name, balance, email, and password
    // public static String getName(long id){
    //     String[] info = getAccountInfo(id);
    //     String name = info[1];
    //     return name;
    // }
    // public static Double getBalance(long id){
    //     String[] info = getAccountInfo(id);
    //     Double balance = Double.parseDouble(info[2]);
    //     return balance;
    // }
    // public static String getEmail(long id){
    //     String[] info = getAccountInfo(id);
    //     String email = info[3];
    //     return email;
    // }
    // public static String getPassword(long id){
    //     String[] info = getAccountInfo(id);
    //     String password = info[4];
    //     return password;
    // }
    public static String getName(){
        String[] info = getAccountInfo();
        String name = info[1];
        return name;
    }
    public static long getID(){
        String[] info = getAccountInfo();
        long id = Long.parseLong(info[0]);
        return id;
    }
    public static Double getBalance(){
        String[] info = getAccountInfo();
        Double balance = Double.parseDouble(info[2]);
        return balance;
    }
    public static String getEmail(){
        String[] info = getAccountInfo();
        String email = info[3];
        return email;
    }
    public static String getPassword(){
        String[] info = getAccountInfo();
        String password = info[4];
        return password;
    }

    /* Adds deposit to the balance of an account. 
     * If deposit is negative, false is returned to indicate an error in the deposit.
     * If the deposit is successful, AllAccounts.txt file is updated with the new balance and true is returned.
    */
    // public static boolean deposit(long id, double deposit, String date, String category){
    //     //date format: month/date/year
    //     double balance = getBalance(id);
    //     if(deposit<0){return false;}
    //     balance += deposit;
    //     updateBalance(id, balance);
    //     try{
    //         String fileName = id + ".txt";
    //         File f = new File(fileName);
    //         FileWriter fr = new FileWriter(f, true);
    //         PrintWriter pr = new PrintWriter(fr);
    //         pr.println(date + "|" + category + "|+" + deposit);
    //         fr.close();
    //         pr.close();
    //         return true;
    //     }
    //     catch(Exception e){
    //         return false;
    //     }
    // }
    public static boolean deposit(double deposit, String date, String category){
        //date format: month/date/year
        double balance = getBalance();
        if(deposit<0){return false;}
        balance += deposit;
        long id = getID();
        updateBalance(balance);
        try{
            String fileName = id + ".txt";
            File f = new File(fileName);
            FileWriter fr = new FileWriter(f, true);
            PrintWriter pr = new PrintWriter(fr);
            pr.println(date + "|" + category + "|+" + deposit);
            fr.close();
            pr.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    /* Attempts to withdraw given amount to the balance of an account. 
     * If withdrawl is successful, AllAccounts.txt file is updated with the new balance and true is returned.
     * If the withdrawl amount is negative or higher than account balance, false is returned
    */
    // public static boolean withdrawl(long id, double withdrawl, String date, String category){
    //     double balance = getBalance(id);
    //     if(withdrawl<0 || withdrawl > balance){
    //         return false;
    //     }
    //     balance-=withdrawl;
    //     updateBalance(id, balance);
    //     try{
    //         String fileName = id + ".txt";
    //         File f = new File(fileName);
    //         FileWriter fr = new FileWriter(f, true);
    //         PrintWriter pr = new PrintWriter(fr);
    //         pr.println(date + "|" + category + "|-" + withdrawl);
    //         fr.close();
    //         pr.close();
    //         return true;
    //     }
    //     catch(Exception e){
    //         return false;
    //     }
    // }
    public static boolean withdrawl(double withdrawl, String date, String category){
        double balance = getBalance();
        if(withdrawl<0 || withdrawl > balance){
            return false;
        }
        balance-=withdrawl;
        long id = getID();
        updateBalance(balance);
        try{
            String fileName = id + ".txt";
            File f = new File(fileName);
            FileWriter fr = new FileWriter(f, true);
            PrintWriter pr = new PrintWriter(fr);
            pr.println(date + "|" + category + "|-" + withdrawl);
            fr.close();
            pr.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    /* Finds the nextID given the used Account IDs in the AllAccounts.txt file.
     * If file is empty, 1001 is returned for the first account.
     */
    //updated
    public static long getNextID(){
        try{
            File file = new File("AllAccounts.txt");
            Scanner scanner = new Scanner(file);
            long num = 1000;
            while(scanner.hasNextLine()){
                String[] line = scanner.nextLine().split("\\|");
                if(num<Long.parseLong(line[0])){
                    num = Long.parseLong(line[0]);
                }
            }
            scanner.close();
            return num+1;
        }
        catch(Exception e){
            return 1001;
        }
    }

    // Returns an ArrayList of Strings of all the Accounts in AllAccounts.txt
    //no updates
    public static ArrayList<String> getAccountList(){
        ArrayList<String> array = new ArrayList<String>();
        try{
            File file = new File("AllAccounts.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                array.add(scanner.nextLine());
            }
            scanner.close();
            return array;
        }
        catch(Exception e){
            return array;
        }
    }

    /* Searches through ArrayList created by getAccountList() to find Account information for given ID 
     * Returns an Array with Account information
     * Array ordering: id, name, balance, email, password
    */
    // public static String[] getAccountInfo(long id){
    //     ArrayList<String> accounts = getAccountList();
    //     for(String info: accounts){
    //         String[] accountInfo = info.split("\\|");
    //         if(Long.parseLong(accountInfo[0]) == id){
    //             return accountInfo;
    //         }
    //     }
    //     return null;
    // }
    public static String[] getAccountInfo(){
        try{
            File file = new File("AllAccounts.txt");
            Scanner scanner = new Scanner(file);
            String[] info = scanner.nextLine().split("\\|");
            scanner.close();
            return info;
        }
        catch(Exception e){
            return null;
        }
    }

    // Updates AllAccounts.txt with an ID's new balance
    // public static void updateBalance(long id, double newBalance){
    //     ArrayList<String> accounts = getAccountList();
    //     for(int i = 0; i<accounts.size(); i++){
    //         String info = accounts.get(i);
    //         String[] accountInfo = info.split("\\|");
    //         if(Long.parseLong(accountInfo[0]) == id){
    //             accountInfo[2] = newBalance+"";
    //             String change = id + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
    //             accounts.set(i, change);
    //         }
    //     }
    //     try{
    //         File file = new File("AllAccounts.txt");
    //         FileWriter fr = new FileWriter(file, false);
    //         PrintWriter pr = new PrintWriter(fr);
    //         for(String info: accounts){
    //             pr.println(info);
    //         }
    //         fr.close();
    //         pr.close();
    //     }
    //     catch(Exception e){
    //         return;
    //     }
    // }
    public static void updateBalance(double newBalance){
        ArrayList<String> accounts = getAccountList();
        String[] accountInfo = getAccountInfo();
        accountInfo[2] = newBalance+"";
        String change = accountInfo[0] + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
        accounts.set(0, change);
        try{
            File file = new File("AllAccounts.txt");
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
    

    // Updates AllAccounts.txt with an ID's new name
    // public static void updateName(long id, String newName){
    //     ArrayList<String> accounts = getAccountList();
    //     for(int i = 0; i<accounts.size(); i++){
    //         String info = accounts.get(i);
    //         String[] accountInfo = info.split("\\|");
    //         if(Long.parseLong(accountInfo[0]) == id){
    //             accountInfo[1] = newName+"";
    //             String change = id + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
    //             accounts.set(i, change);
    //         }
    //     }
    //     try{
    //         File file = new File("AllAccounts.txt");
    //         FileWriter fr = new FileWriter(file, false);
    //         PrintWriter pr = new PrintWriter(fr);
    //         for(String info: accounts){
    //             pr.println(info);
    //         }
    //         fr.close();
    //         pr.close();
    //     }
    //     catch(Exception e){
    //         return;
    //     }
    // }
    public static void updateName(String newName){
        ArrayList<String> accounts = getAccountList();
        String[] accountInfo = getAccountInfo();
        accountInfo[1] = newName;
        String change = accountInfo[0] + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
        accounts.set(0, change);
        try{
            File file = new File("AllAccounts.txt");
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

    // Updates AllAccounts.txt with an ID's new email
    // public static void updateEmail(long id, String newEmail){
    //     //send verification email
    //     ArrayList<String> accounts = getAccountList();
    //     for(int i = 0; i<accounts.size(); i++){
    //         String info = accounts.get(i);
    //         String[] accountInfo = info.split("\\|");
    //         if(Long.parseLong(accountInfo[0]) == id){
    //             accountInfo[3] = newEmail+"";
    //             String change = id + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
    //             accounts.set(i, change);
    //         }
    //     }
    //     try{
    //         File file = new File("AllAccounts.txt");
    //         FileWriter fr = new FileWriter(file, false);
    //         PrintWriter pr = new PrintWriter(fr);
    //         for(String info: accounts){
    //             pr.println(info);
    //         }
    //         fr.close();
    //         pr.close();
    //     }
    //     catch(Exception e){
    //         return;
    //     }
    // }
    public static void updateEmail(String newEmail){
        ArrayList<String> accounts = getAccountList();
        String[] accountInfo = getAccountInfo();
        accountInfo[3] = newEmail;
        String change = accountInfo[0] + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
        accounts.set(0, change);
        try{
            File file = new File("AllAccounts.txt");
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

    // Updates AllAccounts.txt with an ID's new password
    // public static boolean updatePassword(long id, String attemptCurrPassword, String newPassword){
    //     String[] currInfo = getAccountInfo(id);
    //     String currPassword = currInfo[4];
    //     if(!(currPassword.equals(attemptCurrPassword))){return false;}
    //     ArrayList<String> accounts = getAccountList();
    //     for(int i = 0; i<accounts.size(); i++){
    //         String info = accounts.get(i);
    //         String[] accountInfo = info.split("\\|");
    //         if(Long.parseLong(accountInfo[0]) == id){
    //             accountInfo[4] = newPassword+"";
    //             String change = id + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
    //             accounts.set(i, change);
    //         }
    //     }
    //     try{
    //         File file = new File("AllAccounts.txt");
    //         FileWriter fr = new FileWriter(file, false);
    //         PrintWriter pr = new PrintWriter(fr);
    //         for(String info: accounts){
    //             pr.println(info);
    //         }
    //         fr.close();
    //         pr.close();
    //         return true;
    //     }
    //     catch(Exception e){
    //         return false;
    //     }
    // }
    public static boolean updatePassword(String attemptCurrPassword, String newPassword){
        String[] accountInfo = getAccountInfo();
        String currPassword = accountInfo[4];
        if(!(currPassword.equals(attemptCurrPassword))){return false;}
        ArrayList<String> accounts = getAccountList();
        accountInfo[4] = newPassword;
        String change = accountInfo[0] + "|" + accountInfo[1] + "|" + accountInfo[2] + "|" + accountInfo[3] + "|" + accountInfo[4];
        accounts.set(0, change);
        try{
            File file = new File("AllAccounts.txt");
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String info: accounts){
                pr.println(info);
            }
            fr.close();
            pr.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    // Deletes a transaction in id.txt
    // public static void deleteTransaction(long id, String delete){
    //     ArrayList<String> transactions = allTransactions(id);
    //     transactions.remove(delete);
    //     try{
    //         String fileName = id + ".txt";
    //         File file = new File(fileName);
    //         FileWriter fr = new FileWriter(file, false);
    //         PrintWriter pr = new PrintWriter(fr);
    //         for(String transaction: transactions){
    //             pr.println(transaction);
    //         }
    //         fr.close();
    //         pr.close();
    //     }
    //     catch(Exception e){
    //         return;
    //     }
    // }
    public static void deleteTransaction(String delete){
        long id = getID();
        ArrayList<String> transactions = allTransactions();
        transactions.remove(delete);
        try{
            String fileName = id + ".txt";
            File file = new File(fileName);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String transaction: transactions){
                pr.println(transaction);
            }
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }

    // Updates transaction date in id.txt
    public static void updateTransactionDate(String update, String newDate){
        long id = getID();
        ArrayList<String> transactions = allTransactions();
        String[] transactionInfo = update.split("\\|");
        transactionInfo[0] = newDate;
        String newTransaction = transactionInfo[0] + "|" + transactionInfo[1] + "|" + transactionInfo[2];
        int index = transactions.indexOf(update);
        transactions.set(index, newTransaction);
        try{
            String fileName = id + ".txt";
            File file = new File(fileName);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String transaction: transactions){
                pr.println(transaction);
            }
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }

    // Updates transaction type in id.txt
    public static void updateTransactionType(String update, String newType){
        long id = getID();
        ArrayList<String> transactions = allTransactions();
        String[] transactionInfo = update.split("\\|");
        transactionInfo[1] = newType;
        String newTransaction = transactionInfo[0] + "|" + transactionInfo[1] + "|" + transactionInfo[2];
        int index = transactions.indexOf(update);
        transactions.set(index, newTransaction);
        try{
            String fileName = id + ".txt";
            File file = new File(fileName);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String transaction: transactions){
                pr.println(transaction);
            }
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }

    // Updates a transaction amount in id.txt and updates balance based on new transaction amount
    public static void updateTransactionAmount(String update, double newAmount){
        long id = getID();
        ArrayList<String> transactions = allTransactions();
        String[] transactionInfo = update.split("\\|");
        double balance = getBalance();
        double oldAmount = Double.parseDouble(transactionInfo[2]);
        balance -= oldAmount;
        balance += newAmount;
        updateBalance(balance);
        transactionInfo[2] = newAmount +"";
        String newTransaction = transactionInfo[0] + "|" + transactionInfo[1] + "|" + transactionInfo[2];
        int index = transactions.indexOf(update);
        transactions.set(index, newTransaction);
        try{
            String fileName = id + ".txt";
            File file = new File(fileName);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pr = new PrintWriter(fr);
            for(String transaction: transactions){
                pr.println(transaction);
            }
            fr.close();
            pr.close();
        }
        catch(Exception e){
            return;
        }
    }

    // Takes an ID, opens its file, and returns all of the transactions in the file as an ArrayList
    public static ArrayList<String> allTransactions(){
        long id = getID();
        ArrayList<String> array = new ArrayList<String>();
        String fileName = id + ".txt";
        try{
            File f = new File(fileName);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                array.add(sc.nextLine());
            }
            sc.close();
            return array;
        }
        catch(Exception e){
            return array;
        }
    }
    
    // Takes an ArrayList of transactions and filters it given a starting and ending date
    public static ArrayList<String> filteredTransactionsDate(ArrayList<String> transactions, String startDate, String endDate){
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
    
    // Takes an ArrayList of transactions and filters it given a type of transaction
    public static ArrayList<String> filteredTransactionsType(ArrayList<String> transactions, String type){
        for(int i = transactions.size()-1; i>=0; i--){
            String transaction = transactions.get(i);
            String[] transactionInfo = transaction.split("\\|");
            if(!(transactionInfo[1].equals(type))){
                transactions.remove(i);
            }
        }
        return transactions;
    }

    // Sorts an ArrayList of transactions by earliest to latest date
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
    
    // Converts a String date to a Double value (days since the year 0)
    public static double dateToDouble(String date){
        //index 0 is month, index 1 is day, index 2 is year
        String[] array = date.split("/");
        double days = Double.parseDouble(array[2])*365.25 + (Double.parseDouble(array[0])/12)*365.25 + Double.parseDouble(array[1]);
        return days;
    }

    // Returns a string for a webpage that the user might be trying to access based on their help desk search
    public static String helpDesk(String question){
        String[] words = question.split(" ");
        for(String word: words){
            if(word.equals("add") || word.equals("deposit") || word.equals("withdraw") || word.equals("take")){
                return "deposit";
            }
            if(word.equals("transaction") || word.equals("transactions") || word.equals("history") || word.equals("past") || word.equals("find")){
                return "transactions";
            }
            if(word.equals("balance") || word.equals("current") || word.equals("info")){
                return "balance";
            }
        }
        return null;
    }

    // Validates an account given the user ID and password
    public static boolean validate(long id, String password){
        try{
            File file = new File("AllAccounts.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] info = scanner.nextLine().split("\\|");
                long accID = Long.parseLong(info[0]);
                String accPW = info[4];
                if(id == accID && password.equals(accPW)){
                    scanner.close();
                    setIdToTop(id);
                    return true;
                }
            }
            scanner.close();
            return false;
        }
        catch(Exception e){
            return false;
        }
    }
    public static void setIdToTop(long id){
        ArrayList<String> array = new ArrayList<String>();
        try{
            File file = new File("AllAccounts.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] info = scanner.nextLine().split("\\|");
                long accID = Long.parseLong(info[0]);
                if(id==accID){
                    array.add(0, line);
                }
                else{
                    array.add(line);
                }
            }
            scanner.close();
        }
        catch(Exception e){
            return;
        }
    }

    public static void main(String[] args){
        // createAccount("ezan", 500, "ezan@gmail.com", "ezanspw");
        // createAccount("alyzae", 100.4, "alyzae@gmail.com", "niya");
        // System.out.println(deposit(1001, 50, "10/17/2024", "income", "month of october"));
        // System.out.println(withdrawl(1001, 10, "10/21/2024", "food", "chips"));
        // updatePassword(1002, "niya3.0");
        // updateName(1001, "ezan3.0");
        // System.out.println(getName(1001) + ", " + getBalance(1001) + ", " + getPassword(1001));
        // System.out.println(getName(1002) + ", " + getBalance(1002) + ", " + getPassword(1002));
        // deposit(1001, 50, "10/19/2024", "income");
        // System.out.println(filteredTransactionsType(allTransactions(1001), "income"));
        // System.out.println(sortTransactionsDate(allTransactions(1001)));
        // updateTransactionAmount(1001, "10/19/2024|income|+50.0", "-10");
        // updateTransactionAmount(1001, "10/17/2024|income|+10.0", -20);
        // System.out.println(allTransactions(1001));
        
    }
}

