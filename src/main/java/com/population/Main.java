package com.population;

public class Main {
    public static void main(String[] args) {

        // ================================
        // LANGUAGE STATISTICS REPORT
        // ================================
        LanguageStatsService langReport = new LanguageStatsService();

        System.out.println("\n=== Language Statistics (Speakers & % of World Population) ===");
        langReport.printLanguageStats();  // ✅ Correct method call

        // ================================
        // DISCONNECT DATABASE
        // ================================
        Database.disconnect();
    }
}
