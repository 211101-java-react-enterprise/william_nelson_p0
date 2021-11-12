package com.revature.banking.screens;

import com.revature.banking.Services.UserService;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {

    private final UserService userService;

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {

        // TODO Implement user options and make this screen loop until the user logs out. //Loop not working
        AppUser sessionUser = userService.getSessionUser();

        if (sessionUser == null) {
            System.out.println("You are not currently logged in! Navigating to Login Screen");
            router.navigate("/login");
            return;
        }

        //Looping Variable.
        while (userService.isSessionActive()) {
            System.out.printf("\n%s's Dashboard\n", sessionUser.getFirstName());

            String menu = "1) View/edit my account information\n" +
                          "2) Check current balance\n" +
                          "3) ...\n" +
                          "4) Logout\n" +
                          "> ";

            System.out.print(menu);

            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case "1":
                    System.out.println("Account selected");
                    break;
                case "2":
                    System.out.println("Balance selected");
                    break;
                case "3":
                    System.out.println("... selected");
                    break;
                case "4":
                    userService.logout();
                    break;
                default:
                    System.out.println("The user made an invalid selection");
            }
        }


    }
}