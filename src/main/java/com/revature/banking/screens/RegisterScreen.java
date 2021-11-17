package com.revature.banking.screens;

//TODO Finished
import com.revature.banking.exceptions.InvalidRequestException;
import com.revature.banking.exceptions.ResourcePersistenceException;
import com.revature.banking.services.UserService;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.ScreenRouter;
import com.revature.banking.util.logging.Logger;


import java.io.BufferedReader;

public class RegisterScreen extends Screen {

    //Needs a field that stores a user.
    private final UserService userService;
    private final Logger logger;

    //Constructor that calls the Screen Consturtero with SUPER, and then initializes the new field.
    public RegisterScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, Logger logger) {
        super("RegisterScreen", "/register", consoleReader, router);
        this.userService = userService;
        this.logger = logger;
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
            System.out.println("User registered, please log in to use features!");
        } catch (InvalidRequestException e) {
            logger.logAndPrint("You have provided invalid data. Please try again.");
        } catch (ResourcePersistenceException e) {
            System.out.println("Username or Email already taken");
            logger.logAndPrint("There was an issue when trying to persist the user to the datasource.");
        }


    }
}
