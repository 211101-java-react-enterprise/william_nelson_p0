package com.revature.banking.util;

//TODO Review logic and commit to memory

//This Class Establishes and runs a connection to a DB and gives access to the DB instance connection


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final ConnectionFactory connectionFactory = new ConnectionFactory();

    //This file obscures our database connection from Repo and Source code
    private Properties props = new Properties();

    //Forces Driver for SQL DB JAR
    static {
        try {
            // Forcibly load the PostgreSQL Driver into JVM memory so that it can create a connection
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private ConnectionFactory() {
        try {
            props.load(new FileReader("src/main/resources/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        return connectionFactory;
    }

    public Connection getConnection() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }


}
