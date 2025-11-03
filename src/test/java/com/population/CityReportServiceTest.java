package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CityReportService.
 * These tests verify that all report methods execute successfully
 * without throwing runtime exceptions.
 *
 * Author: Israel Ayemo
 */
public class CityReportServiceTest {

    @Test
    void testListAllCities_ExecutesWithoutError() {
        CityReportService service = new CityReportService();
        assertDoesNotThrow(service::listAllCities,
                "Method should execute without throwing exceptions");
    }

    @Test
    void testTopNCitiesInWorld_ExecutesWithoutError() {
        CityReportService service = new CityReportService();
        assertDoesNotThrow(() -> service.topNCitiesInWorld(5),
                "Top N cities method should run without errors");
    }

    @Test
    void testListCitiesByContinent_ExecutesWithoutError() {
        CityReportService service = new CityReportService();
        assertDoesNotThrow(() -> service.listCitiesByContinent("Europe"),
                "Listing cities by continent should not throw errors");
    }

    @Test
    void testListCitiesByRegion_ExecutesWithoutError() {
        CityReportService service = new CityReportService();
        assertDoesNotThrow(() -> service.listCitiesByRegion("Eastern Asia"),
                "Listing cities by region should not throw errors");
    }

    @Test
    void testListCitiesByCountry_ExecutesWithoutError() {
        CityReportService service = new CityReportService();
        assertDoesNotThrow(() -> service.listCitiesByCountry("France"),
                "Listing cities by country should not throw errors");
    }

    @Test
    void testListCitiesByDistrict_ExecutesWithoutError() {
        CityReportService service = new CityReportService();
        assertDoesNotThrow(() -> service.listCitiesByDistrict("Texas"),
                "Listing cities by district should not throw errors");
    }
}
