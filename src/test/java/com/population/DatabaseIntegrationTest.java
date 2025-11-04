package com.population;

import org.junit.jupiter.api.Test;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseIntegrationTest {

    @Test
    void testDatabaseConnection() {
        Connection conn = Database.connect();
        assertNotNull(conn, "Database connection should not be null");
        Database.disconnect();
    }

    @Test
    void testQueryCountryCount() {
        Connection conn = Database.connect();
        assertNotNull(conn, "Database connection failed");

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM country")) {

            assertTrue(rs.next(), "ResultSet should have at least one row");
            int count = rs.getInt(1);
            assertTrue(count > 0, "Expected at least one country in database");

        } catch (SQLException e) {
            fail("SQL query failed: " + e.getMessage());
        } finally {
            Database.disconnect();
        }

    }
}
