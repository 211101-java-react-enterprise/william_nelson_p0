package com.revature.banking.screens;
//TODO DONE
import com.revature.banking.exceptions.AuthenticationException;
import com.revature.banking.exceptions.InvalidRequestException;
import com.revature.banking.services.UserService;
import com.revature.banking.util.ScreenRouter;
import com.revature.banking.util.logging.Logger;


import java.io.BufferedReader;

public class LoginScreen extends Screen{


        private final UserService userService;
        private final Logger logger;

        public LoginScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, Logger logger) {
            super("LoginScreen", "/login", consoleReader, router);
            this.userService = userService;
            this.logger = logger;
        }

        @Override
        public void render() throws Exception {

            System.out.println("Please provide your credentials to log into your account.");
            System.out.print("Username > ");
            String username = consoleReader.readLine();
            System.out.print("Password > ");
            String password = consoleReader.readLine();

            logger.log("User Credentials entered. Attempting Authentication");

            try {
                userService.authenticateUser(username, password);
                router.navigate("/dashboard");
            } catch (InvalidRequestException | AuthenticationException e) {
                logger.logAndPrint(e.getMessage());
            }

        }

    }