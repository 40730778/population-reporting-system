package com.population;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the Database connection.
 * This test checks if the system can connect to the MySQL database.
 * Tagged as "integration" so it can be skipped during CI builds.
 */
@Tag("integration")
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
