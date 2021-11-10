package com.revature.banking.main.app;

import com.revature.banking.models.AppUser;
import com.revature.banking.utils.AppState;


import java.io.*;

public class BankApp {

    static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    //static newUser = new AppUser;

    public static void main(String[] args) {
            AppState app = new AppState();
            app.startup();
    }
}