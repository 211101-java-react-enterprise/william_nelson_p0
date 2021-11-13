package com.revature.banking.Services;

//This service handles all validation of transactions that can be made on accounts
//Deposit
//Withdraw
//Close Account


import com.revature.banking.DAOS.TransactionsDAO;
import com.revature.banking.models.Transaction;

public class TransactionService {

    private final TransactionsDAO transactionDAO;
    private final AccountService accountService;
    private final UserService userService;


    public TransactionService (TransactionsDAO transactionDAO, AccountService accountService, UserService userService){
        this.transactionDAO = transactionDAO;
        this.accountService = accountService;
        this.userService = userService;
    }


    //Validate Transaction Data
    public boolean isTransactionValid(Transaction tran){
        return (tran != null);
    }








}
