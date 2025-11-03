package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PopulationReportService.
 * These tests confirm that population report methods
 * execute successfully without throwing runtime exceptions.
 *
 * Author: Israel Ayemo
 */
public class PopulationReportServiceTest {

    @Test
    void testPopulationOfWorld_ExecutesWithoutError() {
        PopulationReportService service = new PopulationReportService();
        assertDoesNotThrow(service::populationOfWorld,
                "Population of world method should execute without errors");
    }

    @Test
    void testPopulationOfContinent_ExecutesWithoutError() {
        PopulationReportService service = new PopulationReportService();
        assertDoesNotThrow(() -> service.populationOfContinent("Europe"),
                "Population of continent method should execute without errors");
    }

    @Test
    void testPopulationOfRegion_ExecutesWithoutError() {
        PopulationReportService service = new PopulationReportService();
        assertDoesNotThrow(() -> service.populationOfRegion("Western Europe"),
                "Population of region method should execute without errors");
    }

    @Test
    void testPopulationOfCountry_ExecutesWithoutError() {
        PopulationReportService service = new PopulationReportService();
        assertDoesNotThrow(() -> service.populationOfCountry("France"),
                "Population of country method should execute without errors");
    }

    @Test
    void testPopulationOfDistrict_ExecutesWithoutError() {
        PopulationReportService service = new PopulationReportService();
        assertDoesNotThrow(() -> service.populationOfDistrict("California"),
                "Population of district method should execute without errors");
    }

    @Test
    void testPopulationOfCity_ExecutesWithoutError() {
        PopulationReportService service = new PopulationReportService();
        assertDoesNotThrow(() -> service.populationOfCity("Tokyo"),
                "Population of city method should execute without errors");
    }
}
