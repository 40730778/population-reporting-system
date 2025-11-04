package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for LanguageStatsService
 */
public class LanguageStatsServiceTest {

    @Test
    void testGetLanguageStats_DoesNotThrow() {
        LanguageStatsService service = new LanguageStatsService();
        assertDoesNotThrow(service::printLanguageStats,
                "Fetching and printing language stats should not throw errors");
    }
}
