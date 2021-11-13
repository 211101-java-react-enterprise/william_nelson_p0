package com.revature.banking.screens;

import com.revature.banking.Services.AccountService;
import com.revature.banking.Services.TransactionService;
import com.revature.banking.Services.UserService;
import com.revature.banking.models.Account;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.LinkedList;
import com.revature.banking.util.List;
import com.revature.banking.util.ScreenRouter;
import sun.awt.image.ImageWatched;

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
                    successAndReturn();
                    break;



                case "2":
                    System.out.println("Deposit");
                    //Logic
                    //Get Account to remove from. Get Account to deposit too.
                    //Verify user is on both accounts in some way.
                    //Find Accounts in DB by Username and/or Status(Joint or Primary)
                    //Get amount to move. Verify not negative or > account balance.
                    //Change balances if data is good.
                    successAndReturn();
                    break;

                case "3":
                    System.out.println("Withdraw selected");
                    //Logic
                    //Get account type and status to withdraw from
                    //find related accounts, if none handle logic
                    //Display balance and request amount to withdraw
                    //verify non-negative
                    //verify not greater than current balance
                    //do math
                    //write to database
                   successAndReturn();
                    break;

                case "4":
                    System.out.println("Returning to Dashboard");
                    router.navigate("/dashboard");
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

    protected void successAndReturn() throws Exception{
        System.out.println("Transaction successful, continue? Y/N?");
        String transactAgain = consoleReader.readLine();
        if (transactAgain.equals("Y") || (transactAgain.equals("y")) || (transactAgain.equals("yes")) || (transactAgain.equals("YES"))) {
            System.out.println("Returning to Transactions");
        } else {
            System.out.println("Returning to dashboard.");
            router.navigate("/dashboard");
        }

    }

}
