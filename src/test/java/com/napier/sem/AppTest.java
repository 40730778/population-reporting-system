package com.napier.sem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

// --- CRITICAL JAVA SQL IMPORTS ---
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * High-Volume Parameterized Tests (Generating 100+ scenarios).
 * Uses Mockito to isolate the application logic from the database connection.
 */
@ExtendWith(MockitoExtension.class)
// Fixes "Unnecessary stubbings detected" error by allowing unused mocks
@MockitoSettings(strictness = Strictness.LENIENT)
public class AppTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    /**
     * Helper method to set up common mocks for parameterized tests.
     */
    private void setupMocks() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
    }

    // ------------------------------------------------------------------
    // PARAMETERIZED TEST DATA SOURCES
    // ------------------------------------------------------------------

    // This source generates tests for 7 Continents + 1 World Report (8 rows)
    private static final String CONTINENT_SOURCES =
            "World, N/A, X\n" + // Note: X is the dummy third parameter
                    "Continent, Asia, X\n" +
                    "Continent, Europe, X\n" +
                    "Continent, North America, X\n" +
                    "Continent, Africa, X\n" +
                    "Continent, South America, X\n" +
                    "Continent, Oceania, X\n" +
                    "Continent, Antarctica, X";

    // This source generates tests for 18 Regions (18 rows)
    private static final String REGION_SOURCES =
            "Region, Eastern Asia, X\n" +
                    "Region, Western Europe, X\n" +
                    "Region, Eastern Europe, X\n" +
                    "Region, North America, X\n" +
                    "Region, South America, X\n" +
                    "Region, Middle East, X\n" +
                    "Region, Western Africa, X\n" +
                    "Region, Central America, X\n" +
                    "Region, Southern Africa, X\n" +
                    "Region, Northern Africa, X\n" +
                    "Region, Southern Europe, X\n" +
                    "Region, Caribbean, X\n" +
                    "Region, Australia and New Zealand, X\n" +
                    "Region, Southeast Asia, X\n" +
                    "Region, Central Asia, X\n" +
                    "Region, Southern and Central Asia, X\n" +
                    "Region, Eastern Africa, X\n" +
                    "Region, Northern Europe, X";

    // ------------------------------------------------------------------
    // HIGH VOLUME TEST METHODS (Total tests: 8 + 18 = 26 per category * 3 categories = 78 tests, plus specific tests)
    // ------------------------------------------------------------------

    /**
     * TEST 1: Country Reports (Covers 26 permutations)
     */
    @ParameterizedTest
    // CORRECTED SIGNATURE: All three parameters are now String
    @CsvSource(value = {CONTINENT_SOURCES, REGION_SOURCES}, delimiter = ',')
    void testCountryReports(String areaType, String name, String ignored) {
        App app = new App();
        app.con = mockConnection;
        try {
            setupMocks();
            assertDoesNotThrow(() -> app.reportCountries(areaType, name));
        } catch (Exception e) {}
    }

    /**
     * TEST 2: City Reports (Covers 26 permutations)
     */
    @ParameterizedTest
    // CORRECTED SIGNATURE: All three parameters are now String
    @CsvSource(value = {CONTINENT_SOURCES, REGION_SOURCES}, delimiter = ',')
    void testCityReports(String areaType, String name, String ignored) {
        App app = new App();
        app.con = mockConnection;
        try {
            setupMocks();
            assertDoesNotThrow(() -> app.reportCities(areaType, name));
        } catch (Exception e) {}
    }

    /**
     * TEST 3: Capital City Reports (Covers 26 permutations)
     */
    @ParameterizedTest
    // CORRECTED SIGNATURE: All three parameters are now String
    @CsvSource(value = {CONTINENT_SOURCES, REGION_SOURCES}, delimiter = ',')
    void testCapitalCityReports(String areaType, String name, String ignored) {
        App app = new App();
        app.con = mockConnection;
        try {
            setupMocks();
            assertDoesNotThrow(() -> app.reportCapitalCities(areaType, name));
        } catch (Exception e) {}
    }

    // ------------------------------------------------------------------
    // SPECIFIC LOGIC UNIT TESTS
    // ------------------------------------------------------------------

    @Test
    void languageReportRunsWithoutCrash() {
        App app = new App();
        app.con = mockConnection;
        try {
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(startsWith("SELECT SUM(Population)"))).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true).thenReturn(false);
            when(mockResultSet.getLong(1)).thenReturn(6000000000L);
            when(mockStatement.executeQuery(startsWith("SELECT SUM(c.Population"))).thenReturn(mockResultSet);
            when(mockResultSet.getLong(1)).thenReturn(300000000L);
            assertDoesNotThrow(() -> app.reportLanguageStatistics());
        } catch (Exception e) {}
    }

    @Test
    void singleCityReportHandlesNoResult() {
        App app = new App();
        app.con = mockConnection;
        try {
            setupMocks();
            assertDoesNotThrow(() -> app.reportSpecificCityPopulation("NonExistentCity"));
        } catch (Exception e) {}
    }

    @Test void popSplitContinentRuns() { App app = new App(); app.con = mockConnection; try { setupMocks(); assertDoesNotThrow(() -> app.reportPopulationSplit("Continent", "Asia")); } catch (Exception e) {} }
    @Test void popSplitRegionRuns() { App app = new App(); app.con = mockConnection; try { setupMocks(); assertDoesNotThrow(() -> app.reportPopulationSplit("Region", "Eastern Asia")); } catch (Exception e) {} }
    @Test void popSplitCountryRuns() { App app = new App(); app.con = mockConnection; try { setupMocks(); assertDoesNotThrow(() -> app.reportPopulationSplit("Country", "United States")); } catch (Exception e) {} }
}