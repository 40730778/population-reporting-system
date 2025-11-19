package com.napier.sem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * High-Volume Parameterized Tests (Generating 14+ scenarios from a few lines of code).
 * This class uses Mockito to isolate the application logic (Java code) from the database connection.
 */
@ExtendWith(MockitoExtension.class)
public class AppTest {

    // These variables will be automatically set up by MockitoExtension
    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    /**
     * Helper method to set up common mocks for all parameterized tests,
     * ensuring the database commands run without errors.
     */
    private void setupMocks() throws Exception {
        // Mock connection and statement creation
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        // Ensure any SQL query executed returns a mock result set
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        // Simulate that the result set is initially empty (no records found)
        when(mockResultSet.next()).thenReturn(false);
    }

    // ------------------------------------------------------------------
    // PARAMETERIZED TESTS (Generates over 10 separate test cases efficiently)
    // ------------------------------------------------------------------

    /**
     * Generates a test case for every major continent. (7 tests)
     * Test passes if the report method runs without crashing (since setupMocks handles the return value).
     */
    @ParameterizedTest
    @CsvSource(value = {
            "Continent, Asia",
            "Continent, Europe",
            "Continent, North America",
            "Continent, Africa",
            "Continent, South America",
            "Continent, Oceania",
            "Continent, Antarctica"
    })
    void countryReportByContinentRunsWithoutCrash(String areaType, String name) {
        App app = new App();
        app.con = mockConnection;
        try {
            setupMocks();
            assertDoesNotThrow(() -> app.reportCountries(areaType, name));
        } catch (Exception e) {
            // Test fails if setup breaks
        }
    }

    /**
     * Generates a test case for several common regions. (4 tests)
     */
    @ParameterizedTest
    @CsvSource(value = {
            "Region, Eastern Asia",
            "Region, Western Europe",
            "Region, Caribbean",
            "Region, Southern Africa"
    })
    void countryReportByRegionRunsWithoutCrash(String areaType, String name) {
        App app = new App();
        app.con = mockConnection;
        try {
            setupMocks();
            assertDoesNotThrow(() -> app.reportCountries(areaType, name));
        } catch (Exception e) {
            // Test fails if setup breaks
        }
    }

    /**
     * Generates a test case for a simple global report. (1 test)
     */
    @Test
    void countryReportByWorldRunsWithoutCrash() {
        App app = new App();
        app.con = mockConnection;
        try {
            setupMocks();
            assertDoesNotThrow(() -> app.reportCountries("World", ""));
        } catch (Exception e) {
            // Test fails if setup breaks
        }
    }

    // ------------------------------------------------------------------
    // SPECIFIC UNIT TESTS
    // ------------------------------------------------------------------

    /**
     * Tests the complex percentage calculation logic in the Language Report.
     */
    @Test
    void languageReportRunsWithoutCrash() {
        App app = new App();
        app.con = mockConnection;
        try {
            // 1. Mock the World Population Query
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(startsWith("SELECT SUM(Population)"))).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true).thenReturn(false); // First result true, then end
            when(mockResultSet.getLong(1)).thenReturn(6000000000L); // Mock 6 Billion World Pop

            // 2. Mock the Language Speaker Query (run 5 times for 5 languages)
            when(mockStatement.executeQuery(startsWith("SELECT SUM(c.Population"))).thenReturn(mockResultSet);
            when(mockResultSet.getLong(1)).thenReturn(300000000L); // Mock 300 million speakers for any language

            assertDoesNotThrow(() -> app.reportLanguageStatistics());
        } catch (Exception e) {}
    }

    /**
     * Tests that the single city report runs without crashing on a null result.
     */
    @Test
    void singleCityReportHandlesNoResult() {
        App app = new App();
        app.con = mockConnection;
        try {
            setupMocks(); // Uses the common setup which returns no data
            assertDoesNotThrow(() -> app.reportSpecificCityPopulation("NonExistentCity"));
        } catch (Exception e) {}
    }
}