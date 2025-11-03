package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CountryReportService.
 * These tests verify that report methods execute without throwing exceptions.
 * (Compatible with void-returning service methods)
 *
 * Author: Israel Ayemo
 */
public class CountryReportServiceTest {

    @Test
    void testListAllCountries_NotNull() {
        CountryReportService service = new CountryReportService();
        assertDoesNotThrow(service::listAllCountries,
                "Method should execute without throwing exceptions");
    }

    @Test
    void testTopNCountriesInWorld_NotNull() {
        CountryReportService service = new CountryReportService();
        assertDoesNotThrow(() -> service.topNCountriesInWorld(5),
                "Top N countries report should run without errors");
    }

    @Test
    void testListCountriesByContinent_NotNull() {
        CountryReportService service = new CountryReportService();
        assertDoesNotThrow(() -> service.listCountriesByContinent("Europe"),
                "List by continent should run without errors");
    }

    @Test
    void testListCountriesByRegion_NotNull() {
        CountryReportService service = new CountryReportService();
        assertDoesNotThrow(() -> service.listCountriesByRegion("Eastern Asia"),
                "List by region should run without errors");
    }
}
