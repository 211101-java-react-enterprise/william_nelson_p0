package com.revature.banking.Services;
//TODO Write Test Cases and Implement

//This service handles all validation of transactions that can be made on accounts
//Deposit
//Withdraw
//Close Account


import com.revature.banking.DAOS.TransactionsDAO;
import com.revature.banking.Exceptions.InputErrorException;
import com.revature.banking.Exceptions.InvalidRequestException;
import com.revature.banking.models.Account;
import com.revature.banking.models.Transaction;
import com.revature.banking.util.collections.List;
import com.revature.banking.util.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class TransactionService {

    private final TransactionsDAO transactionDAO;
    private final AccountService accountService;
    private final UserService userService;
    private final Logger logger;


    public TransactionService(TransactionsDAO transactionDAO, AccountService accountService, UserService userService, Logger logger) {
        this.transactionDAO = transactionDAO;
        this.accountService = accountService;
        this.userService = userService;
        this.logger = logger;
    }


    public void getBalance(BufferedReader consoleReader, AccountService accountService) throws Exception {

        System.out.println("Check Balance");
        List<Account> accountList = accountService.findMyAccounts();

        boolean loopIfIncorrectSelection = false;

        do {
            System.out.println("Which balances do you want?");
            for (int i = 0; i < accountList.size(); i++) {
                System.out.println((i + 1) + " " + accountList.get(i).getName());
            }

            System.out.println("\nPlease select an number next to an account: ");
            int selection = Integer.parseInt(consoleReader.readLine());

            if (selection > accountList.size()) {
                System.out.println("Please select a number that corresponds to an account on the list");
                loopIfIncorrectSelection = true;
            } else {
                Double BalanceSelection = accountList.get((selection - 1)).getBalance();

                System.out.println("Selected Balance is: " + BalanceSelection);
                logger.log("Balance Check Successful");
                loopIfIncorrectSelection = false;
            }
        } while (loopIfIncorrectSelection == true);

    }


    public boolean isDepositAmountValid(Double amount) {
        if (amount == null || amount <= 0) return false;
        if (amount > 0) {
            //Convert Doulbe to a string of consisting of integers and a . for the decimal spot.
            String text = Double.toString(Math.abs(amount));
            //Get the index of the . which will be the same number of integers before the .
            int integerPlaces = text.indexOf('.');
            //Math the remaining spots after the .
            int decimalPlaces = text.length() - integerPlaces - 1;

            if (decimalPlaces > 2) {
                String msg = "Please use correct format of $####.## when entering values";
                logger.logAndPrint(msg);
                return false;
            }
        }

        logger.log("deposit valid");
        return true;


    }

    public void deposit(BufferedReader consoleReader, TransactionService transactionService) throws IOException {
        System.out.println("Deposit initiated!");
        //Get Account.
        List<Account> accountList = accountService.findMyAccounts();

        boolean loopIfIncorrectSelection = false;

        do {

            //Print List of Accounts to be deposited into.
            System.out.println("Which Account do you want to deposit into?");
            displayAccounts(accountList);


            //get user selection
            String str = consoleReader.readLine();
            int selection = -1;
            boolean valid_int = selectionValidation(str);

            if (valid_int){
                selection = Integer.parseInt(str);
            }
            else {
                logger.logAndPrint("Selection Invalid, rerouting to list of accounts.");
            }

            //Should not run unless int_valid
            if (selection > accountList.size()) {
                System.out.println("Please select a number that corresponds to an account on the list");
                loopIfIncorrectSelection = true;
            }else if (selection <= 0)
            {
                System.out.println("Please select a number that corresponds to an account on the list");
                loopIfIncorrectSelection = true;
            }
            else {
                //Find Appropriate Balance and print it.
                Double BalanceSelection = accountList.get((selection - 1)).getBalance();
                System.out.println("Selected Balance is: " + BalanceSelection);

                //Request Update Deposit Ammount
                System.out.println("How much do you want to deposit? :  ");
                Double depositAmount = Double.parseDouble(consoleReader.readLine());
                //Check for Valid Deposit ammount

                 if (transactionService.isDepositAmountValid(depositAmount) && (depositAmount != 0)) {
                        Double depositTotal = depositAmount + BalanceSelection;

                        //Make and Configure account object.
                        Transaction newTran = new Transaction();
                        newTran.setOwner(userService.getSessionUser());
                        newTran.setType("Deposit");
                        newTran.setAmount(depositAmount);
                        newTran.setNewBalance(depositTotal);
                        newTran.setAccount(accountList.get(selection - 1));
                        newTran.setOwner(accountList.get(selection - 1).getOwner());
                        Transaction processedTransaction = transactionDAO.save(newTran);

                        //Should be written to DB now. Update the balance on the account
                        Account updaterAccount = accountList.get(selection - 1);
                        updaterAccount.setBalance(depositTotal);
                        accountService.updateOldAccount(updaterAccount);
                        logger.log("Deposit Successful");

                    } else {
                        logger.logAndPrint("Error in deposit process");
                    }
                 loopIfIncorrectSelection = false; //Break loop
            }
        } while (loopIfIncorrectSelection == true);
        logger.log("End of Deposit");
    }


    public void withdraw(BufferedReader consoleReader, TransactionService transactionService) throws Exception {
        System.out.println("Withdraw initiated!");
        //Get Account.
        List<Account> accountList = accountService.findMyAccounts();

        boolean loopIfIncorrectSelection = false;

        do {
            //Print List of Accounts to be deposited into.
            displayAccounts(accountList);

            //get user selection
            String str = consoleReader.readLine();
            int selection = -1;
            boolean valid_int = selectionValidation(str);

            if (valid_int){
                selection = Integer.parseInt(str);
            }
            else {
                logger.logAndPrint("Selection Invalid, rerouting to list of accounts.");
            }

            if (selection > accountList.size()) {
                System.out.println("Please select a number that corresponds to an account on the list");
                loopIfIncorrectSelection = true;
            } else {
                //Find Appropriate Balance and print it.
                Double BalanceSelection = accountList.get((selection - 1)).getBalance();
                System.out.println("Selected Balance is: " + BalanceSelection);

                //Request Update Withdrawal Amount
                System.out.println("How much do you want to withdraw? :  ");
                Double withdrawAmount = Double.parseDouble(consoleReader.readLine());
                //Check for Valid Withdraw amount

                if (transactionService.isWithdrawAmountValid(withdrawAmount, BalanceSelection)) {
                    Double withdrawTotal = BalanceSelection - withdrawAmount;

                    //Make and Configure account object.
                    Transaction newTran = new Transaction();
                    newTran.setOwner(userService.getSessionUser());
                    newTran.setType("Withdraw");
                    newTran.setAmount(withdrawAmount);
                    newTran.setNewBalance(withdrawTotal);
                    newTran.setAccount(accountList.get(selection - 1));
                    newTran.setOwner(accountList.get(selection - 1).getOwner());

                    Transaction processedTransaction = transactionDAO.save(newTran);
                    //Should be written to DB now. Update the balance on the account
                    Account updaterAccount = accountList.get(selection - 1);
                    updaterAccount.setBalance(withdrawTotal);
                    accountService.updateOldAccount(updaterAccount);
                } else {
                    logger.logAndPrint("Error in withdraw process.");
                    logger.logAndPrint("Returning to transactions.");
                    logger.log("Withdraw ended");
                }
            }
            loopIfIncorrectSelection = false;// Break loop if we get to this validation

    } while(loopIfIncorrectSelection);
}


    private boolean isWithdrawAmountValid(Double amount, Double currentBalance) {
        if (amount == null || amount <= 0 || currentBalance == 0 || currentBalance < 0 ) return false;
        if (amount > 0)
        {
            //Check Valid Number
            String text = Double.toString(Math.abs(amount));
            int integerPlaces = text.indexOf('.');
            int decimalPlaces = text.length() - integerPlaces - 1;

            //If not Valid
            if(decimalPlaces > 2) {
                String msg = "Please use correct format of $####.## when entering values";
                logger.logAndPrint(msg);
                return false;
            } else if (currentBalance - amount < 0) {
                String msg = "Amount selected would result in overdrawing, canceling transaction";
                logger.logAndPrint(msg);
                return false;
            }

        }
        logger.log("Withdrawal Valid");
        return true;
    }

    public void displayAccounts(List<Account> accountList){
        for (int i = 0; i < accountList.size(); i++) {
            System.out.println((i + 1) + " " + accountList.get(i).getName());
        }
    }

    //Function to ensure a string given is an int and that it can be used in Integer.parseInt
    public boolean selectionValidation(String argStr) {
        try {
            @SuppressWarnings("unused")
            int x = Integer.parseInt(argStr);
            return true; //String is an Integer
        } catch (NumberFormatException e) {
            return false; //String is not an Integer
        }
    }


}






