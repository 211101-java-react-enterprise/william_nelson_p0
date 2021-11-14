package com.revature.banking.screens;

//TODO DONE

import com.revature.banking.util.ScreenRouter;
import com.revature.banking.util.logging.Logger;

import static com.revature.banking.util.AppState.shutdown;
import java.io.BufferedReader;



public class WelcomeScreen extends Screen {

    private final Logger logger;

    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router, Logger logger) {
        super("WelcomeScreen", "/welcome", consoleReader, router);
        this.logger = logger;
    }

    @Override
    public void render() throws Exception {

        System.out.print("Welcome to Quizzard!\n" +
                "1) Login\n" +
                "2) Register\n" +
                "3) Exit\n" +
                "> ");

        String userSelection = consoleReader.readLine();

        logger.log("Welcome Screen Rendered and user selected " + userSelection + ".");

        switch (userSelection) {
            case "1":
                router.navigate("/login");
                break;
            case "2":
                router.navigate("/register");
                break;
            case "3":
                System.out.println("Exiting application...");
                shutdown();
                logger.log("APPLICATION END ATTEMPTED APPROPRIATELY");
                break;
            case "throw exception":
                throw new RuntimeException(); // "throw" is used to explicitly throw an exception that will (hopefully) be handled elsewhere
            default:
                System.out.println("The user made an invalid selection");
        }
    }
}
