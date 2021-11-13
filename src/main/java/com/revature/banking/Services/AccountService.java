package com.revature.banking.Services;

import com.revature.banking.DAOS.AccountDAO;
import com.revature.banking.Exceptions.AuthorizationException;
import com.revature.banking.Exceptions.InvalidRequestException;
import com.revature.banking.Exceptions.ResourcePersistenceException;
import com.revature.banking.models.Account;
import com.revature.banking.util.List;

public class AccountService {

    //Working AccountDAO for working with accounts on the DB, and a user service that we can use
    //to have a link to the session user and user service functions.
    private final AccountDAO accountDAO;
    private final UserService userService;

    public AccountService(AccountDAO accountDAO, UserService userService) {
        this.accountDAO = accountDAO;
        this.userService = userService;
    }

    //Get a list of accounts by owner ID
    public List<Account> findMyAccounts() {

        if (!userService.isSessionActive()) {
            throw new AuthorizationException("No active user session to perform operation!");
        }

        return accountDAO.findAccountsByOwnerId(userService.getSessionUser().getId());

    }


    //Create New Account
    public void createNewAccount(Account newAccount) {
        if (!isAccountValid(newAccount)) {
            throw new InvalidRequestException("Invalid Account Information");
        }

        //Save the new account
        newAccount.setOwner(userService.getSessionUser());
        Account createdAccount = accountDAO.save(newAccount); //Figure this out

        if (createdAccount == null){
            throw new ResourcePersistenceException("Account could not be created!");
        }

    }



   //Just checking that an account type has been selected.
    public boolean isAccountValid(Account account){
        if (account == null) return false;
        return (account.getType() != null && !account.getType().trim().equals(""));
    }


}
