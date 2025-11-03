package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CapitalCityReportService.
 * These tests verify that report methods execute successfully
 * without throwing runtime exceptions.
 *
 * Author: Israel Ayemo
 */
public class CapitalCityReportServiceTest {

    @Test
    void testListAllCapitalCities_ExecutesWithoutError() {
        CapitalCityReportService service = new CapitalCityReportService();
        assertDoesNotThrow(service::listAllCapitalCities,
                "Listing all capital cities should not throw errors");
    }

    @Test
    void testTopNCapitalCitiesInWorld_ExecutesWithoutError() {
        CapitalCityReportService service = new CapitalCityReportService();
        assertDoesNotThrow(() -> service.topNCapitalCitiesInWorld(5),
                "Listing top N capital cities should not throw errors");
    }

    @Test
    void testListCapitalCitiesByContinent_ExecutesWithoutError() {
        CapitalCityReportService service = new CapitalCityReportService();
        assertDoesNotThrow(() -> service.listCapitalCitiesByContinent("Asia"),
                "Listing capital cities by continent should not throw errors");
    }

    @Test
    void testListCapitalCitiesByRegion_ExecutesWithoutError() {
        CapitalCityReportService service = new CapitalCityReportService();
        assertDoesNotThrow(() -> service.listCapitalCitiesByRegion("Western Europe"),
                "Listing capital cities by region should not throw errors");
    }
}
