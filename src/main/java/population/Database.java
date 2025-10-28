package com.population;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    public static Connection connect() {
        try {
            // ✅ Use MySQL Driver (you already have it installed)
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/world",
                        "root",
                        "theo"
                );
                System.out.println("✅ Connected to database.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect: " + e.getMessage());
        }
        return connection;
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Disconnected.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error closing: " + e.getMessage());
        }
    }
}
