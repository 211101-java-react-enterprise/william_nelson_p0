package com.revature.banking.services;
//TODO Test Casing to be implented and tried.

import com.revature.banking.DAOS.AccountDAO;
import com.revature.banking.exceptions.AuthorizationException;
import com.revature.banking.exceptions.InvalidRequestException;
import com.revature.banking.exceptions.ResourcePersistenceException;
import com.revature.banking.models.Account;
import com.revature.banking.util.collections.LinkedList;
import com.revature.banking.util.collections.List;
import com.revature.banking.util.logging.Logger;

public class AccountService {

    //Working AccountDAO for working with accounts on the DB, and a user service that we can use
    //to have a link to the session user and user service functions.
    private final AccountDAO accountDAO;
    private final UserService userService;
    private final Logger logger;

    public AccountService(AccountDAO accountDAO, UserService userService, Logger logger) {
        this.accountDAO = accountDAO;
        this.userService = userService;
        this.logger = logger;
    }

    //Get a list of accounts by owner ID
    public List<Account> findMyAccounts() throws RuntimeException {

        if (!userService.isSessionActive()) {
            String msg = "No active user session to perform operation!";
            logger.logAndPrint(msg);
            throw new AuthorizationException(msg);
        }

        logger.log("Found Accounts by logged in user");
        List<Account> accountList = new LinkedList<>();
        accountList = accountDAO.findAccountsByOwnerId((userService.getSessionUser().getId()));

        return accountList;


    }


    //Create New Account
    public void createNewAccount(Account newAccount) {
        if (!isAccountValid(newAccount)) {
            String msg = "Invalid Account Information";
            logger.logAndPrint(msg);
            throw new InvalidRequestException(msg);
        }

        //Save the new account
        newAccount.setOwner(userService.getSessionUser());
        Account createdAccount = accountDAO.save(newAccount);



        if (createdAccount == null){
            String msg = "Account could not be created!";
            logger.logAndPrint(msg);
            throw new ResourcePersistenceException(msg);
        }

        logger.log("Account saved");

    }

    public void updateOldAccount (Account update_info) {

        if (!isAccountValid(update_info)){
            String msg = "Account could not be updated";
            logger.logAndPrint(msg);
            throw new ResourcePersistenceException(msg);
        }

        accountDAO.update(update_info);
        logger.log("Account Updated");
    }



   //Just checking that an account type has been selected.
    public boolean isAccountValid(Account account){
        if (account == null) return false;
        return (account.getType() != null && !account.getType().trim().equals(""));
    }


}
