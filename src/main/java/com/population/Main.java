package com.population;

public class Main {
    public static void main(String[] args) {
        try {
            // ✅ Create an instance of your report service
            CountryReportService countryReport = new CountryReportService();

            // ✅ 1. All countries in the world
            System.out.println("=== All Countries in the World by Population (DESC) ===");
            countryReport.listAllCountries();

            // ✅ 2. Countries in a specific continent
            System.out.println("\n=== European Countries by Population (DESC) ===");
            countryReport.listCountriesByContinent("Europe");

            // ✅ 3. Countries in a specific region
            System.out.println("\n=== Western Africa - Countries by Population (DESC) ===");
            countryReport.listCountriesByRegion("Western Africa");

            // ✅ 4. Top N most populated countries in the world
            System.out.println("\n=== Top 5 Most Populated Countries in the World ===");
            countryReport.topNCountriesInWorld(5);

            // ✅ 5. Top N most populated countries in a continent
            System.out.println("\n=== Top 3 Most Populated Countries in Asia ===");
            countryReport.topNCountriesInContinent("Asia", 3);

            // ✅ 6. Top N most populated countries in a region
            System.out.println("\n=== Top 2 Most Populated Countries in Western Europe ===");
            countryReport.topNCountriesInRegion("Western Europe", 2);

            // ✅ Disconnect after all reports
            Database.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
