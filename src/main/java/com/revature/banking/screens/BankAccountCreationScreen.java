package com.revature.banking.screens;

//TODO DONE.

import com.revature.banking.Exceptions.InvalidRequestException;
import com.revature.banking.Exceptions.ResourcePersistenceException;
import com.revature.banking.Services.AccountService;
import com.revature.banking.Services.UserService;
import com.revature.banking.models.Account;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.ScreenRouter;
import com.revature.banking.util.logging.Logger;

import java.io.BufferedReader;

public class BankAccountCreationScreen extends Screen {

    private final UserService userService;
    private final AccountService accountService;
    private final Logger logger;


    public BankAccountCreationScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService, Logger logger) {
        super("AccountCreationScreen", "/AccountCreation", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
        this.logger = logger;
    }

    @Override
    public void render() throws Exception {
        logger.log("Rendering Account Screen");
        logger.log("Getting Session User");
        AppUser sessionUser = userService.getSessionUser();

        if (sessionUser == null) {

            logger.logAndPrint("You are not currently logged in! Navigating to Login Screen");
            router.navigate("/login");
            return;
        }

        //Only Display if Session is Active and logged in.
        while (userService.isSessionActive()) {
            System.out.println("Account Creation Menu");

            String menu = "1) Create new checking account\n" +
                          "2) Create new savings account\n" +
                          "3) Back to Dashboard\n" +
                           "> ";

            System.out.print(menu);

            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case "1":
                 accountCreationMachine("Checking");
                    break;
                case "2":
                accountCreationMachine("Savings");
                    break;
                case "3":
                    System.out.println("Returning to Dashboard");
                    router.navigate("/dashboard");
                    break;
                default:
                    System.out.println("The user made an invalid selection");


            }
        }
    }

    //Method to display account creation was successful and to get back to dashboard for further transactions.
    //Avoiding copy and paste.
    protected void successAndReturn() throws Exception{
        System.out.println("Account Created, Returning to Dashboard");
        this.router.navigate("/dashboard");
    }

    protected void accountCreationMachine(String type) throws Exception{
        logger.log("Creation Machine Starting...");
        logger.logAndPrint("Creating new " + type +  " account");
        System.out.println("Please enter an account name!");
        String accountName = consoleReader.readLine();
        logger.log("Account Name " + accountName + ". Nice!");

        Account newAccount = new Account(); //New account with zero balance no type.
        newAccount.setType(type);
        newAccount.setName(accountName);

        logger.log("Dummy Account Created. Attempting to write to Database");

        try {
            accountService.createNewAccount(newAccount);
            successAndReturn();
        } catch (InvalidRequestException | ResourcePersistenceException e) {
            logger.logAndPrint(e.getMessage());
        }
    }

}
