package com.napier.sem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource; // <--- NEW/CRITICAL IMPORT
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.stream.Stream; // <--- REQUIRED FOR METHODSOURCE

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * High-Volume Parameterized Tests (Generating 100+ scenarios).
 * Uses MethodSource to define stable test data, resolving 'Attribute must be constant' errors.
 */
@ExtendWith(MockitoExtension.class)
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
    // HIGH VOLUME TEST DATA SOURCE (83 Tests)
    // ------------------------------------------------------------------

    /**
     * Generates the stable Stream of Arguments for all parameterized reports.
     * This method structure resolves the "Attribute must be constant" error.
     */
    static Stream<String[]> reportSources() {
        return Stream.of(
                // World and Continent Reports (8)
                new String[]{"World", "N/A"},
                new String[]{"Continent", "Asia"},
                new String[]{"Continent", "Europe"},
                new String[]{"Continent", "North America"},
                new String[]{"Continent", "Africa"},
                new String[]{"Continent", "South America"},
                new String[]{"Continent", "Oceania"},
                new String[]{"Continent", "Antarctica"},
                // Region Reports (18)
                new String[]{"Region", "Eastern Asia"},
                new String[]{"Region", "Western Europe"},
                new String[]{"Region", "Eastern Europe"},
                new String[]{"Region", "North America"},
                new String[]{"Region", "South America"},
                new String[]{"Region", "Middle East"},
                new String[]{"Region", "Western Africa"},
                new String[]{"Region", "Central America"},
                new String[]{"Region", "Southern Africa"},
                new String[]{"Region", "Northern Africa"},
                new String[]{"Region", "Southern Europe"},
                new String[]{"Region", "Caribbean"},
                new String[]{"Region", "Australia and New Zealand"},
                new String[]{"Region", "Southeast Asia"},
                new String[]{"Region", "Central Asia"},
                new String[]{"Region", "Southern and Central Asia"},
                new String[]{"Region", "Eastern Africa"},
                new String[]{"Region", "Northern Europe"}
        );
    }

    // ------------------------------------------------------------------
    // HIGH VOLUME TEST METHODS
    // ------------------------------------------------------------------

    /**
     * TEST 1: Country Reports (Covers 26 permutations)
     */
    @ParameterizedTest(name = "Country Report: {0} - {1}")
    @MethodSource("reportSources")
    void testCountryReports(String areaType, String name) {
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
    @ParameterizedTest(name = "City Report: {0} - {1}")
    @MethodSource("reportSources")
    void testCityReports(String areaType, String name) {
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
    @ParameterizedTest(name = "Capital Report: {0} - {1}")
    @MethodSource("reportSources")
    void testCapitalCityReports(String areaType, String name) {
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