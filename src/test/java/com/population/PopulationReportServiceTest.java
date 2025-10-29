package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PopulationReportServiceTest {

    @Test
    void testPopulationByContinent() {
        PopulationReportService service = new PopulationReportService();
        assertDoesNotThrow(service::populationByContinent,
                "Should get population by continent without error");
    }

    @Test
    void testPopulationOfWorld() {
        PopulationReportService service = new PopulationReportService();
        assertDoesNotThrow(service::populationOfWorld,
                "Should get total world population");
    }
}
