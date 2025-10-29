package com.population;

public class Main {
    public static void main(String[] args) {
        try {
            // =====================
// =============================
// LANGUAGE STATISTICS REPORT
// =============================
            LanguageStatsService langReport = new LanguageStatsService();

            System.out.println("\n=== Language Statistics (Speakers & % of World Population) ===");
            langReport.listLanguageStatistics();

            // =============================
            // ✅ DISCONNECT DATABASE
            // =============================
            Database.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }    }
}
