package com.example.springboot.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSingleton {
    // Singleton instance
    private static DatabaseSingleton instance;

    // MySQL database connection parameters
    private static final String URL = "${spring.datasource.url}";
    private static final String USERNAME = "${spring.datasource.username}";
    private static final String PASSWORD = "${spring.datasource.password}";

    // Database connection
    private Connection connection;

    private DatabaseSingleton() {
        try
        {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Method to get the singleton instance
    public static synchronized DatabaseSingleton getInstance() {
        if (instance == null) {
            instance = new DatabaseSingleton();
        }
        return instance;
    }

    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }
}
