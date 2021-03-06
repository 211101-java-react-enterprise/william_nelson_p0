package com.revature.banking.screens;

import com.revature.banking.services.AccountService;
import com.revature.banking.services.TransactionService;
import com.revature.banking.services.UserService;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.ScreenRouter;
import com.revature.banking.util.logging.Logger;

import java.io.BufferedReader;

public class TransactionScreen extends Screen{

    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;
    private final Logger logger;

    public TransactionScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService, TransactionService transactionService, Logger logger) {
        super("TransactionScreen", "/Transactions", consoleReader, router);
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.userService = userService;
        this.logger = logger;
    }

    @Override
    public void render() throws Exception {
        //CheckLogin Function. This is being repeated.
        AppUser sessionUser = userService.getSessionUser();

        if (sessionUser == null) {
            System.out.println("You are not currently logged in! Navigating to Login Screen");
            router.navigate("/login");
            return;
        }

        while (userService.isSessionActive()) {
            System.out.println("Transactions Menu\n");

            String menu = "1) Check balance\n" +
                        "2) Deposit\n" +
                        "3) Withdraw\n" +
                        "4) Return to Dashboard\n" +
                        "> ";

            System.out.print(menu);

            String userSelection = consoleReader.readLine();

            logger.log("Menu printed, user selected " + userSelection + ".");

            switch (userSelection) {
                case "1":
                    try {
                        transactionService.getBalance(consoleReader, accountService);
                    } catch (Exception e) {
                        logger.log(e.getMessage());
                    }
                    successAndOrReturn();
                    break;

                case "2":
                    try {
                        transactionService.deposit(consoleReader, transactionService);
                    } catch (Exception e) {
                        logger.log(e.getMessage());
                    }

                    successAndOrReturn();
                    break;

                case "3":
                    try {
                        transactionService.withdraw(consoleReader, transactionService);
                    } catch (Exception e) {
                        System.out.println("Failure in the withdraw process, please try again");
                        logger.log(e.getMessage());
                    }
                    successAndOrReturn();
                    break;

                case "4":
                    System.out.println("Returning to Dashboard");
                    router.navigate("/dashboard");
                    break;

                default:
                    System.out.println("The user made an invalid selection");
            }
            router.navigate("/dashboard");
        }
    }

    protected void successAndOrReturn() throws Exception{
        System.out.println("Transaction concluded, returning to dashboard!");
    }

}
