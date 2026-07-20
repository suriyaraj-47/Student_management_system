package com.college.management.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:college_management.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Establish connection
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            // Create users table if it doesn't exist
            try (Statement stmt = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS users (" +
                        "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL UNIQUE," +
                        "password TEXT NOT NULL," +
                        "role TEXT NOT NULL CHECK(role IN ('student', 'staff', 'admin'))" +
                        ");";
                stmt.execute(sql);
                System.out.println("Table 'users' is ready.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}

