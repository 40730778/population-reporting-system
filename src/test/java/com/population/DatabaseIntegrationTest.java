package com.population;

import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseIntegrationTest {

    @Test
    void testDatabaseConnection() {
        // Try to connect to the database
        Connection conn = Database.connect();

        // Assert connection worked
        assertNotNull(conn, "Database connection should not be null");

        // Close connection
        Database.disconnect();
    }
}
