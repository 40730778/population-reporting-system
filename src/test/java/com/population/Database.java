package com.population;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class.
 * Handles connecting and disconnecting from the MySQL world database.
 *ss
 * Author: Israel Ayemo
 * Matric: 40730778
 */
public class Database {

    // Store a single shared connection
    private static Connection connection;

    /**
     * Connects to the MySQL database.
     *
     * @return an active database connection
     */
    public static Connection connect() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Update credentials if necessary
            String url = "jdbc:mysql://127.0.0.1:3306/world";
            String username = "root";     // ← your MySQL/HeidiSQL username
            String password = "theo";     // ← your MySQL/HeidiSQL password

            // Create connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println(" Connected to database.");
        } catch (ClassNotFoundException e) {
            System.out.println(" JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(" Failed to connect: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Disconnects from the database.
     */
    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Disconnected.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error closing connection: " + e.getMessage());
        }
    }
}
