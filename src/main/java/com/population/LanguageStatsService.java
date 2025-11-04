package com.population;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles Language Statistics Reports
 * Displays number and percentage of world population
 * speaking Chinese, English, Hindi, Spanish, and Arabic.
 *
 * Author: Israel Ayemo
 * Matric: 40730778
 */
public class LanguageStatsService {

    /**
     * Represents a language statistic.
     */
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

    /**
     * Fetches language statistics from the database.
     *
     * @return list of LanguageStat objects
     */
    private List<LanguageStat> getLanguageStats() {
        List<LanguageStat> stats = new ArrayList<>();
        String sql = """
            SELECT 
                cl.Language AS Language,
                SUM(c.Population * (cl.Percentage / 100)) AS Speakers
            FROM countrylanguage cl
            JOIN country c ON cl.CountryCode = c.Code
            WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')
            GROUP BY cl.Language
            ORDER BY Speakers DESC;
        """;

        String worldSQL = "SELECT SUM(Population) AS Total FROM country;";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {

            // Get world population
            long worldPopulation = 0;
            try (ResultSet rsWorld = stmt.executeQuery(worldSQL)) {
                if (rsWorld.next()) worldPopulation = rsWorld.getLong("Total");
            }

            // Get each language population and percentage
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String lang = rs.getString("Language");
                    long speakers = rs.getLong("Speakers");
                    double percentage = (worldPopulation > 0)
                            ? (speakers * 100.0 / worldPopulation)
                            : 0.0;
                    stats.add(new LanguageStat(lang, speakers, percentage));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching language statistics: " + e.getMessage());
        }

        return stats;
    }

    /**
     * Prints the list of languages with speakers and percentages.
     */
    public void printLanguageStats() {
        List<LanguageStat> items = getLanguageStats();
        System.out.printf("\n🌐 %-10s | %-15s | %-10s%n", "Language", "Speakers", "World %");
        System.out.println("---------------------------------------------------");
        for (LanguageStat ls : items) {
            System.out.printf("🌍 %-10s | %,15d | %6.2f%%%n",
                    ls.language, ls.speakers, ls.percentage);
        }
    }
}
