package com.population;

import com.population.CountryReportService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CountryReportServiceTest {

    @Test
    void testListAllCountries_NotNull() {
        CountryReportService service = new CountryReportService();
        assertDoesNotThrow(service::listAllCountries,
                "Method should execute without throwing exceptions");
    }
}
