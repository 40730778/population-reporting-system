package com.population;

import com.population.CapitalCityReportService;

public class Main {
    public static void main(String[] args) {
        try {
            // =====================================================
            // 🌍 COUNTRY REPORTS (Commented Out)
            // =====================================================
            /*
            CountryReportService countryReport = new CountryReportService();
            countryReport.listAllCountries();
            */

            // =====================================================
            // 🏙️ CITY REPORTS (Commented Out)
            // =====================================================
            /*
            CityReportService cityReport = new CityReportService();
            cityReport.listAllCities();
            */

            // =====================================================
            // 🏛️ CAPITAL CITY REPORTS (Active)
            // =====================================================
            CapitalCityReportService capitalReport = new CapitalCityReportService();

            System.out.println("\n=== All Capital Cities in the World by Population (DESC) ===");
            capitalReport.listAllCapitalCities();

            System.out.println("\n=== All Capital Cities in Asia by Population (DESC) ===");
            capitalReport.listCapitalCitiesByContinent("Asia");

            System.out.println("\n=== All Capital Cities in Western Europe by Population (DESC) ===");
            capitalReport.listCapitalCitiesByRegion("Western Europe");

            System.out.println("\n=== Top 5 Most Populated Capital Cities in the World ===");
            capitalReport.topNCapitalCitiesInWorld(5);

            System.out.println("\n=== Top 3 Most Populated Capital Cities in Asia ===");
            capitalReport.topNCapitalCitiesInContinent("Asia", 3);

            System.out.println("\n=== Top 2 Most Populated Capital Cities in Western Europe ===");
            capitalReport.topNCapitalCitiesInRegion("Western Europe", 2);

            System.out.println("\n=== Top 1 Most Populated Capital City in United Kingdom ===");
            capitalReport.topNCapitalCitiesInCountry("United Kingdom", 1);


            // =====================================================
            // ✅ DISCONNECT DATABASE
            // =====================================================
            Database.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
