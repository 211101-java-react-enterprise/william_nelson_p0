package com.revature.banking.util;

import com.revature.banking.DAOS.AccountDAO;
import com.revature.banking.DAOS.AppUserDAO;
import com.revature.banking.DAOS.TransactionsDAO;
import com.revature.banking.Services.AccountService;
import com.revature.banking.Services.TransactionService;
import com.revature.banking.Services.UserService;
import com.revature.banking.screens.*;
import com.revature.banking.util.logging.Logger;

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

    private final Logger logger;
    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {

        logger = Logger.getLogger(false);
        logger.log("Initializing application");

        appRunning = true;
        router = new ScreenRouter(logger);

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO, logger);

        AccountDAO accountDAO = new AccountDAO();
        AccountService accountService = new AccountService(accountDAO, userService, logger);

        TransactionsDAO transDAO = new TransactionsDAO();
        TransactionService transactionService= new TransactionService(transDAO, accountService, userService, logger);

        router.addScreen(new WelcomeScreen(consoleReader, router, logger));
        router.addScreen(new RegisterScreen(consoleReader, router, userService, logger));
        router.addScreen(new LoginScreen(consoleReader, router, userService, logger));
        router.addScreen(new DashboardScreen(consoleReader, router, userService, logger));
        router.addScreen((new BankAccountCreationScreen(consoleReader, router, userService, accountService, logger)));
        router.addScreen((new TransactionScreen(consoleReader, router, userService, accountService, transactionService, logger)));

        logger.log("Application initialized!");

    }

    public void startup() {

        try {
            while (appRunning) {
                router.navigate("/welcome");
                logger.log("Initial navigation succeeded");
            }
            } catch (Exception e) {
               logger.log((e.getMessage()));
            }
    }

    public static void shutdown() {
        appRunning = false;
    }
}