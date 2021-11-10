package com.revature.banking.screens;
//This class is created to blueprint what every screen
//Needs at its base to function
//All Screens should extend this class


import com.revature.banking.utils.ScreenRouter;

import java.io.BufferedReader;
import java.nio.Buffer;

public abstract class Screen {
    //Name of the Screen
    protected String name;
    //Route path to call the screen
    protected String route;
    //Way for screen to read from the console
    protected BufferedReader consoleReader;
    //Routing object that holds the current route
    protected ScreenRouter router;


    //Screen Constructor
    public Screen(String name, String route, BufferedReader consoleReader, ScreenRouter router){
        this.name = name;
        this.route = route;
        this.consoleReader = consoleReader;
        this.router = router;

    }

    //getters that can't be extended by child classes
    public final String getName() {return name;}
    public final String getRoute() {return route;}

    //A method stub used to pass render implementation to child classes so screens can be different.
    public abstract void render() throws Exception;



}
