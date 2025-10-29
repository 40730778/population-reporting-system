package com.population;

import com.population.Database;
import com.population.util.ReportPrinter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageStatsService {

    // Helper class for table display
    private static class LanguageStat {
        String language;
        long speakers;
        double percentage;

        LanguageStat(String language, long speakers, double percentage) {
            this.language = language;
            this.speakers = speakers;
            this.percentage = percentage;
        }
    }

    // Helper to print nicely formatted results
    private void print(List<LanguageStat> items) {
        List<String[]> rows = new ArrayList<>();
        for (LanguageStat ls : items) {
            rows.add(new String[]{
                    ls.language,
                    String.format("%,d", ls.speakers),
                    String.format("%.2f%%", ls.percentage)
            });
        }
        ReportPrinter.printTable(rows, "Language", "Speakers", "% of World");
    }

    // =====================================================
    // REPORT 29–30: Language Statistics
    // =====================================================
    public void listLanguageStatistics() {
        String sql = """
            SELECT cl.Language AS Language,
                   SUM(c.Population * cl.Percentage / 100) AS Speakers,
                   (SUM(c.Population * cl.Percentage / 100) / 
                       (SELECT SUM(Population) FROM country) * 100) AS WorldPercentage
            FROM countrylanguage cl
            JOIN country c ON cl.CountryCode = c.Code
            WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')
            GROUP BY cl.Language
            ORDER BY Speakers DESC;
        """;

        List<LanguageStat> list = new ArrayList<>();

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new LanguageStat(
                        rs.getString("Language"),
                        rs.getLong("Speakers"),
                        rs.getDouble("WorldPercentage")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Query failed: " + e.getMessage());
        }

        print(list);
    }
}
