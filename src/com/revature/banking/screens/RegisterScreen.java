package com.revature.banking.screens;

import com.revature.banking.Exceptions.InvalidRequestException;
import com.revature.banking.Exceptions.ResourcePersistenceException;
import com.revature.banking.Services.UserService;
import com.revature.banking.models.AppUser;
import com.revature.banking.utils.ScreenRouter;

import java.io.BufferedReader;

public class RegisterScreen extends Screen {

    //Needs a field that stores a user.
    private final UserService userService;

    //Constructor that calls the Screen Consturtero with SUPER, and then initializes the new field.
    public RegisterScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("RegisterScreen", "/register", consoleReader, router);
        this.userService = userService;
    }

    //This function Prints and controls the console screen as the register screen
    @Override
    public void render() throws Exception {
        System.out.println("The user selected Register");
        System.out.println("Please provide us with some basic information.");
        System.out.print("First name: ");
        String firstName = consoleReader.readLine();

        System.out.print("Last name: ");
        String lastName = consoleReader.readLine();

        System.out.print("Email: ");
        String email = consoleReader.readLine();

        System.out.print("Username: ");
        String username = consoleReader.readLine();

        System.out.print("Password: ");
        String password = consoleReader.readLine();

        System.out.printf("Provided user first and last name: { \"firstName\": %s, \"lastName\": %s}\n", firstName, lastName);
        // String format specifiers: %s (strings), %d (whole numbers), %f (decimal values)

        AppUser newUser = new AppUser(firstName, lastName, email, username, password);

        try {
            userService.registerNewUser(newUser);
            System.out.println(newUser);
        } catch (InvalidRequestException e) {
            System.out.println("You have provided invalid data. Please try again.");
        } catch (ResourcePersistenceException e) {
            System.out.println("There was an issue when trying to persist the user to the datasource.");
        }


    }
}
