package com.revature.banking.screens;

import com.revature.banking.Services.UserService;
import com.revature.banking.util.ScreenRouter;


import java.io.BufferedReader;

public class RestoreAccessScreen extends Screen{
    public RestoreAccessScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("RestoreAccessScreen", "/restoreAccess", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        //Strech goal

    }
}
