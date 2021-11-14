package com.revature.banking.Services;

//This service handles all validation of transactions that can be made on accounts
//Deposit
//Withdraw
//Close Account


import com.revature.banking.DAOS.TransactionsDAO;
import com.revature.banking.Exceptions.InputErrorException;
import com.revature.banking.models.Account;
import com.revature.banking.models.Transaction;
import com.revature.banking.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;

public class TransactionService {

    private final TransactionsDAO transactionDAO;
    private final AccountService accountService;
    private final UserService userService;


    public TransactionService (TransactionsDAO transactionDAO, AccountService accountService, UserService userService){
        this.transactionDAO = transactionDAO;
        this.accountService = accountService;
        this.userService = userService;
    }


  /*  //Validate Transaction Data
    public boolean isTransactionValid(Transaction tran){
        return (tran != null);
    } */

    public void getBalance(BufferedReader consoleReader, AccountService accountService) throws Exception {

        System.out.println("Check Balance");
        List<Account> accountList = accountService.findMyAccounts();

        System.out.println("Which balances do you want?");
        for (int i = 0; i < accountList.size(); i++) {
            System.out.println((i + 1) + " " + accountList.get(i).getName());
        }

        System.out.println("\nPlease select an number next to an account: ");

        int selection = Integer.parseInt(consoleReader.readLine());

        Double BalanceSelection = accountList.get((selection - 1)).getBalance();

        System.out.println("Selected Balance is: " + BalanceSelection);
    }


    public boolean isDepositAmountValid (Double amount){
        if (amount == null || amount < 0) return false;
        if (amount > 0)
        {
            //Convert Doulbe to a string of consisting of integers and a . for the decimal spot.
            String text = Double.toString(Math.abs(amount));
            //Get the index of the . which will be the same number of integers before the .
            int integerPlaces = text.indexOf('.');
            //Math the remaining spots after the .
            int decimalPlaces = text.length() - integerPlaces - 1;

            if(decimalPlaces > 2) {
                throw new InputErrorException("Please use correct format of $####.## when entering values");
            }
        }

        return true;



       /* A double is not always an exact representation. You can only say how many decimal places you would have if you converted it to a String.

        double d= 234.12413;
        String text = Double.toString(Math.abs(d));
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;*/

    }

    public void deposit (BufferedReader consoleReader, TransactionService transactionService) throws IOException {
        System.out.println("Deposit initiated!");
        //Get Account.
        List<Account> accountList = accountService.findMyAccounts();

        //Print List of Accounts to be deposited into.
        System.out.println("Which Account do you want to deposit into?");
        for (int i = 0; i < accountList.size(); i++) {
            System.out.println((i + 1) + " " + accountList.get(i).getName());
        }

        //Get User Selection
        System.out.println("\nPlease select an number next to an account: ");
        int selection = Integer.parseInt(consoleReader.readLine());

        //Find Appropriate Balance and print it.
        Double BalanceSelection = accountList.get((selection - 1)).getBalance();
        System.out.println("Selected Balance is: " + BalanceSelection);

        //Request Update Deposit Ammount
        System.out.println("How much do you want to deposit? :  ");
        Double depositAmount = Double.parseDouble(consoleReader.readLine());
        //Check for Valid Deposit ammount

        boolean depositSuccess = false;
        do {
            try {
                    if (depositAmount == 0) {
                        System.out.println("Zero Amount selected");
                        System.out.println("Rerouting to Transaction Menu");
                        depositSuccess = true; //Break loop
                    } else if (transactionService.isDepositAmountValid(depositAmount) && (depositAmount != 0)) {
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
                        depositSuccess = true; //Break Loop
                    } else {
                        System.out.println("Error in deposit process");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        } while (depositSuccess = false);

    }
}




