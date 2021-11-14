package com.revature.banking.screens;

import com.revature.banking.Services.AccountService;
import com.revature.banking.Services.TransactionService;
import com.revature.banking.Services.UserService;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.ScreenRouter;

import java.io.BufferedReader;

public class TransactionScreen extends Screen{

    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;

    public TransactionScreen(BufferedReader consoleReader, ScreenRouter router,UserService userService, AccountService accountService, TransactionService transactionService) {
        super("TransactionScreen", "/Transactions", consoleReader, router);
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.userService = userService;
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

            switch (userSelection) {
                case "1":
                    try {
                        transactionService.getBalance(consoleReader, accountService);
                        successAndOrReturn();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "2":
                    try {
                        transactionService.deposit(consoleReader, transactionService);
                        successAndOrReturn();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "3":
                    try {
                        transactionService.withdraw(consoleReader, transactionService);
                        successAndOrReturn();
                    } catch (Exception e) {
                        System.out.println("Failure in the withdraw process, please try again");
                        e.printStackTrace();
                    }
                    break;

                case "4":
                    System.out.println("Returning to Dashboard");
                    router.navigate("/dashboard");
                    break;

                default:
                    System.out.println("The user made an invalid selection");
            }
        }
    }

    protected void successAndOrReturn() throws Exception{
        System.out.println("Transaction concluded, do you want to transact again? Y/N?");
        String transactAgain = consoleReader.readLine();
        if (transactAgain.equals("Y") || (transactAgain.equals("y")) || (transactAgain.equals("yes")) || (transactAgain.equals("YES"))) {
            System.out.println("Returning to Transactions");
        } else {
            System.out.println("Returning to dashboard.");
            router.navigate("/dashboard");
        }

    }

}
