package com.revature.banking.screens;

import com.revature.banking.Services.UserService;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.ScreenRouter;

import java.io.BufferedReader;

public class BankAccountCreationScreen extends Screen {

    private final UserService userService;


    public BankAccountCreationScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("AccountCreationScreen", "/AccountCreation", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        AppUser sessionUser = userService.getSessionUser();

        if (sessionUser == null) {
            System.out.println("You are not currently logged in! Navigating to Login Screen");
            router.navigate("/login");
            return;
        }

        //Only Display if Session is Active and logged in.
        while (userService.isSessionActive()) {
            System.out.printf("\n%s's Dashboard\n", sessionUser.getFirstName());

            String menu = "1) Create new checking account\n" +
                    "2) Create new savings account\n" +
                    "3) Create new investment account\n" +
                    "4) /*Strech Goal of Join Accounts*/\n" +
                    "5) Back to Dashboard\n" +
                    "> ";

            System.out.print(menu);

            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case "1":
                    System.out.println("Creating new checking account");
                    //Logic
                    //Find Session User in DB
                    //Write new account to DB
                    //Ensure User credentials can find account
                    successAndReturn();
                    break;
                case "2":
                    System.out.println("Creating new savings account");
                    //Logic
                    //Find Session User in DB
                    //Write new account to DB
                    //Ensure User credentials can find account
                    successAndReturn();
                    break;
                case "3":
                    System.out.println("Creating new investment account");
                    //Logic
                    //Find Session User in DB
                    //Write new account to DB
                    //Ensure User credentials can find account
                    successAndReturn();
                    break;
                case "4":
                    System.out.println("Creating new joint account");
                    //Logic
                    //Find Session User in DB
                    //Get Credentials for second user
                    //verify second user exists
                    //make new account with session user and add secondary user on account
                    //Ensure User credentials can find account
                    successAndReturn();
                    break;
                case "5":
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
    private void successAndReturn() throws Exception{
        System.out.println("Account Created, returning to dashboard");
        this.router.navigate("/dashboard");
    }

}
