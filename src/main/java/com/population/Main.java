package com.population;

import com.population.CityReportService;

public class Main {
    public static void main(String[] args) {
        try {
            // =============================
            // 🏙️ CITY REPORTS
            // =============================
            CityReportService cityReport = new CityReportService();

            System.out.println("=== All Cities in the World by Population (DESC) ===");
            cityReport.listAllCities();

            System.out.println("\n=== All Cities in Europe by Population (DESC) ===");
            cityReport.listCitiesByContinent("Europe");

            System.out.println("\n=== All Cities in Western Europe by Population (DESC) ===");
            cityReport.listCitiesByRegion("Western Europe");

            System.out.println("\n=== All Cities in the United Kingdom by Population (DESC) ===");
            cityReport.listCitiesByCountry("United Kingdom");

            System.out.println("\n=== All Cities in the District of 'England' by Population (DESC) ===");
            cityReport.listCitiesByDistrict("England");

            System.out.println("\n=== Top 5 Most Populated Cities in the World ===");
            cityReport.topNCitiesInWorld(5);

            System.out.println("\n=== Top 3 Most Populated Cities in Asia ===");
            cityReport.topNCitiesInContinent("Asia", 3);

            System.out.println("\n=== Top 2 Most Populated Cities in Western Europe ===");
            cityReport.topNCitiesInRegion("Western Europe", 2);

            System.out.println("\n=== Top 3 Most Populated Cities in the United Kingdom ===");
            cityReport.topNCitiesInCountry("United Kingdom", 3);

            // =============================
            // ✅ DISCONNECT DATABASE
            // =============================
            Database.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
