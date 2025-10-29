package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CityReportServiceTest {

    @Test
    void testListAllCities() {
        CityReportService service = new CityReportService();
        assertDoesNotThrow(service::listAllCities,
                "Should list all cities without error");
    }

    @Test
    void testTopNCitiesInWorld() {
        CityReportService service = new CityReportService();
        assertDoesNotThrow(() -> service.topNCitiesInWorld(5),
                "Should list top 5 most populated cities");
    }
}
