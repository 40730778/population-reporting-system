package com.population;

public class Main {
    public static void main(String[] args) {
        try {
            // =====================
// POPULATION REPORTS
// =====================
            PopulationReportService populationReport = new PopulationReportService();

            System.out.println("=== Population of each Continent ===");
            populationReport.populationByContinent();

            System.out.println("\n=== Population of each Region ===");
            populationReport.populationByRegion();

            System.out.println("\n=== Population of each Country ===");
            populationReport.populationByCountry();




            // =============================
            // ✅ DISCONNECT DATABASE
            // =============================
            Database.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
