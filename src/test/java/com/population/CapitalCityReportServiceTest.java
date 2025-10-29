package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CapitalCityReportServiceTest {

    @Test
    void testListAllCapitalCities() {
        CapitalCityReportService service = new CapitalCityReportService();
        assertDoesNotThrow(service::listAllCapitalCities,
                "Should list all capital cities without error");
    }

    @Test
    void testTopNCapitalsInWorld() {
        CapitalCityReportService service = new CapitalCityReportService();
        assertDoesNotThrow(() -> service.topNCapitalCitiesInWorld(3),
                "Should list top 3 most populated capital cities");
    }
}
