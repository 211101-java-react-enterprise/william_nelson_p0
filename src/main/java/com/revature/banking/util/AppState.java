package com.revature.banking.util;

import com.revature.banking.DAOS.AccountDAO;
import com.revature.banking.DAOS.AppUserDAO;
import com.revature.banking.DAOS.TransactionsDAO;
import com.revature.banking.Services.AccountService;
import com.revature.banking.Services.TransactionService;
import com.revature.banking.Services.UserService;
import com.revature.banking.screens.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
    Encapsulate of application state. We will create a few things in here that will be used throughout the
    application:
        - a common BufferedReader that all screens can use to read from the console
        - a ScreenRouter that can be used to navigate from one screen to another
        - a boolean that indicates if the app is still running or not
 */

public class AppState {

    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {
        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        AccountDAO accountDAO = new AccountDAO();
        AccountService accountService = new AccountService(accountDAO, userService);

        TransactionsDAO transDAO = new TransactionsDAO();
        TransactionService transactionService= new TransactionService(transDAO, accountService, userService);

         //TODO Determine which screens need the accountService and pass it in.
        router.addScreen(new WelcomeScreen(consoleReader, router));
        router.addScreen(new RegisterScreen(consoleReader, router, userService));
        router.addScreen(new LoginScreen(consoleReader, router, userService));
        router.addScreen(new DashboardScreen(consoleReader, router, userService));
        router.addScreen((new BankAccountCreationScreen(consoleReader, router, userService, accountService)));
        router.addScreen((new TransactionScreen(consoleReader, router, userService, accountService, transactionService)));

    }

    public void startup() {

        try {
            while (appRunning) {
                router.navigate("/welcome");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        appRunning = false;
    }
}