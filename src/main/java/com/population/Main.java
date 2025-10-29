package com.population;

public class Main {
    public static void main(String[] args) {
        try {
            // =====================
// =============================
// POPULATION REPORTS (23–28)
// =============================
            PopulationReportService populationReport = new PopulationReportService();

            System.out.println("\n=== Population Breakdown Reports ===");
            populationReport.populationByContinent();
            populationReport.populationByRegion();
            populationReport.populationByCountry();

            System.out.println("\n=== Population Totals ===");
            populationReport.populationOfWorld();
            populationReport.populationOfContinent("Asia");
            populationReport.populationOfRegion("Western Europe");

            // =============================
            // ✅ DISCONNECT DATABASE
            // =============================
            Database.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
