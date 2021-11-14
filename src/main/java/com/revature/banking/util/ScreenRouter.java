package com.revature.banking.util;

//This Class is used to create a Router between screens
//It Stores a list of screens and all functions related to switching screens
//TODO Finished

import com.revature.banking.screens.Screen;
import com.revature.banking.util.collections.LinkedList;
import com.revature.banking.util.logging.Logger;

public class ScreenRouter {
    //List of Screens the App Can use with routing and rendering
    private final LinkedList<Screen> screens;
    private final Logger logger;

    //Constructor that makes a new list in our router
    public ScreenRouter(Logger logger) {
        screens = new LinkedList<>();
        this.logger = logger;
    }

    //Function to add Screens by giving it a screen via Name
    public void addScreen(Screen screenType) {
        screens.add(screenType);
    }

    //Function to navigate between screens
    public void navigate(String route) throws Exception {

        logger.log("Debug, Screen Size: " + screens.size());
        for (int i = 0; i < screens.size(); i++) {
            //Set a Block screen variable, set it to traversing i, print out current screen.
            Screen thisScreen = screens.get(i);
            logger.log("Debug: " + thisScreen);
            if (thisScreen.getRoute().equals(route)) {
                thisScreen.render();
            }

        }

    }
}


