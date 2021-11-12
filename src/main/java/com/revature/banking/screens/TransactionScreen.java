package com.revature.banking.screens;

import com.revature.banking.Services.UserService;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.ScreenRouter;

import java.io.BufferedReader;

public class TransactionScreen extends Screen{

    private final UserService userService;

    public TransactionScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("TransactionScreen", "/Transactions", consoleReader, router);
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

            String menu = "1) Deposit\n" +
                    "2) Transfer\n" +
                    "3) Withdraw\n" +
                    "4) Return to Dashboard\n" +
                    "> ";

            System.out.print(menu);

            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case "1":
                    System.out.println("Deposit selected");
                    //Logic
                    //Get Account Type
                    //Get Primary or Joint Status
                    //Get Amount to Deposit
                    //verify non-negative. If so clear data and return to Transactions.
                    //Find Account in DB by Username, Type, and Status(Joint or Primary)
                    //Add Deposit to balance.
                    //Write balance to DB.
                    //Ask about more Transactions
                    //Exit As Intended.
                    break;
                case "2":
                    System.out.println("Transfer selected");
                    //Logic
                    //Get Account to remove from. Get Account to deposit too.
                    //Verify user is on both accounts in some way.
                    //Find Accounts in DB by Username and/or Status(Joint or Primary)
                    //Get amount to move. Verify not negative or > account balance.
                    //Change balances if data is good.
                    //Ask about more Transactions
                    //Exit As Intended.
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
                    //Ask about looping
                    //exit if needed
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
}
