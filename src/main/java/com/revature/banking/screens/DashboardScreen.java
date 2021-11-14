package com.revature.banking.screens;
//TODO DONE
import com.revature.banking.Services.UserService;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.ScreenRouter;
import com.revature.banking.util.logging.Logger;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {

    private final UserService userService;
    private final Logger logger;

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, Logger logger) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
        this.userService = userService;
        this.logger = logger;
    }

    @Override
    public void render() throws Exception {

        logger.log("Rendering Dashboard Screen");


        AppUser sessionUser = userService.getSessionUser();

        if (sessionUser == null) {
            System.out.println("You are not currently logged in! Navigating to Login Screen");
            router.navigate("/login");
            return;
        }

        //Looping Variable.
        while (userService.isSessionActive()) {
            logger.log("Dashboard for user to be displayed");
            System.out.printf("\n%s's Dashboard\n", sessionUser.getFirstName());

            String menu = "1) Make account transactions\n" +
                          "2) Create new money accounts\n" +
                          "3) Logout\n" +
                          "> ";

            System.out.print(menu);

            String userSelection = consoleReader.readLine();

            logger.log("User selected " + userSelection + ".");

            switch (userSelection) {
                case "1":
                    System.out.println("Transactions selected");
                    router.navigate("/Transactions");
                    break;
                case "2":
                    System.out.println("Account creation selected");
                    router.navigate("/AccountCreation");
                    break;
                case "3":
                    userService.logout();
                    break;
                default:
                    System.out.println("The user made an invalid selection");
            }
        }


    }
}
