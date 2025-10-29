package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LanguageStatsServiceTest {

    @Test
    void testListLanguageStatistics() {
        LanguageStatsService service = new LanguageStatsService();
        assertDoesNotThrow(service::listLanguageStatistics,
                "Should list all language statistics correctly");
    }
}
