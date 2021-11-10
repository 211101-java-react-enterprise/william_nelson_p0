package com.revature.banking.screens;

import com.revature.banking.Services.UserService;
import com.revature.banking.utils.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen{

    private final UserService userService;

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {

    }
}
