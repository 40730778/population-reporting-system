package com.napier.sem;

import java.sql.*;

public class App {
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     * @param location The database location (e.g., "db:3306" for Docker)
     * @param delay Delay in milliseconds before attempting connection
     */
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "root");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * REQUIREMENT: Language Statistics
     * Number of speakers of Chinese, English, Hindi, Spanish, Arabic
     * and the percentage of the world population.
     */
    public void reportLanguageStatistics() {
        try {
            // 1. Get Total World Population
            long worldPop = 0;
            Statement stmt1 = con.createStatement();
            ResultSet rset1 = stmt1.executeQuery("SELECT SUM(Population) FROM country");
            if (rset1.next()) {
                worldPop = rset1.getLong(1);
            }

            // 2. Define the languages we need
            String[] languages = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};

            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("LANGUAGE STATISTICS REPORT");
            System.out.printf("%-20s %-20s %-20s%n", "Language", "Total Speakers", "% of World Pop");
            System.out.println("--------------------------------------------------------------------------------");

            // 3. Loop through each language and calculate
            for (String lang : languages) {
                // Logic: Sum of (Country Population * Language Percentage)
                String sql = "SELECT SUM(c.Population * (cl.Percentage / 100)) " +
                        "FROM countrylanguage cl " +
                        "JOIN country c ON cl.CountryCode = c.Code " +
                        "WHERE cl.Language = '" + lang + "'";

                Statement stmt2 = con.createStatement();
                ResultSet rset2 = stmt2.executeQuery(sql);

                if (rset2.next()) {
                    long speakers = rset2.getLong(1);
                    double percent = (worldPop > 0) ? ((double)speakers / worldPop) * 100 : 0;
                    System.out.printf("%-20s %-20d %-20.2f%%%n", lang, speakers, percent);
                }
            }
            System.out.println("--------------------------------------------------------------------------------\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get language report.");
        }
    }

    /**
     * REQUIREMENT: Population Split Report (Total vs. In Cities vs. Not in Cities)
     * Generic method to report population breakdown for a given area type (Continent, Region, Country).
     */
    public void reportPopulationSplit(String type, String name) {
        String title = type.toUpperCase() + ": " + name;

        // SQL to get Total Population (A) and City Population (B) for the target area
        String sqlTotalPop = "";
        String sqlCityPop = "";

        // Define SQL based on the type of area (Continent, Region, or Country)
        switch (type.toLowerCase()) {
            case "continent":
            case "region":
                // For Continent/Region, we need the SUM of all countries in that area
                sqlTotalPop = "SELECT SUM(Population) FROM country WHERE " + type + " = '" + name + "'";
                sqlCityPop = "SELECT SUM(city.Population) FROM city c JOIN country co ON c.CountryCode = co.Code WHERE co." + type + " = '" + name + "'";
                break;
            case "country":
                // For a specific country, the population is directly available
                sqlTotalPop = "SELECT Population FROM country WHERE Name = '" + name + "'";
                sqlCityPop = "SELECT SUM(Population) FROM city WHERE CountryCode = (SELECT Code FROM country WHERE Name = '" + name + "')";
                break;
            default:
                System.out.println("Invalid type specified: " + type);
                return;
        }

        try (Statement stmt = con.createStatement()) {
            // A. GET TOTAL POPULATION
            long totalPop = 0;
            ResultSet rsetTotal = stmt.executeQuery(sqlTotalPop);
            if (rsetTotal.next()) {
                totalPop = rsetTotal.getLong(1);
            }

            // B. GET CITY POPULATION
            long popInCities = 0;
            ResultSet rsetCity = stmt.executeQuery(sqlCityPop);
            if (rsetCity.next()) {
                popInCities = rsetCity.getLong(1);
            }

            // C. CALCULATE NON-CITY POPULATION
            long popNotCities = totalPop - popInCities;

            // D. CALCULATE PERCENTAGES
            double percentInCities = (totalPop > 0) ? ((double) popInCities / totalPop) * 100 : 0;
            double percentNotCities = (totalPop > 0) ? ((double) popNotCities / totalPop) * 100 : 0;

            // E. PRINT REPORT
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("POPULATION BREAKDOWN: " + title);
            System.out.printf("%-20s %-20s%n", "Category", "Population");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%-20s %-20d%n", "Total Population", totalPop);
            System.out.printf("%-20s %-20d (%.2f%%)%n", "Living in Cities", popInCities, percentInCities);
            System.out.printf("%-20s %-20d (%.2f%%)%n", "Not in Cities", popNotCities, percentNotCities);
            System.out.println("--------------------------------------------------------------------------------\n");

        } catch (SQLException e) {
            System.out.println("Database error during population split report: " + e.getMessage());
        }
    }

    /**
     * REQUIREMENT: Top N Cities in a District
     * Note: Assumes reports like All Countries/Cities/Capitals are handled by separate methods (not included here).
     */
    public void reportTopNCitiesInDistrict(String district, int n) {
        try {
            Statement stmt = con.createStatement();
            // JOIN with country to get the Country Name instead of Code (Req: Name, Country, District, Pop)
            String strSelect =
                    "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE city.District = '" + district + "' " +
                            "ORDER BY city.Population DESC " +
                            "LIMIT " + n;

            ResultSet rset = stmt.executeQuery(strSelect);

            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("TOP " + n + " CITIES IN DISTRICT: " + district.toUpperCase());
            System.out.printf("%-20s %-30s %-20s %-10s%n", "Name", "Country", "District", "Population");
            System.out.println("--------------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-20s %-30s %-20s %-10s%n",
                        rset.getString("Name"),
                        rset.getString("CountryName"),
                        rset.getString("District"),
                        rset.getInt("Population"));
            }
            System.out.println("--------------------------------------------------------------------------------\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get district report.");
        }
    }

    /**
     * REQUIREMENT: Accessible Information (Single Query - Example for City)
     */
    public void reportSpecificCityPopulation(String cityName) {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT Population FROM city WHERE Name = '" + cityName + "'";
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                System.out.println("--------------------------------------------------");
                System.out.println("POPULATION CHECK: " + cityName);
                System.out.println("Population: " + rset.getLong("Population"));
                System.out.println("--------------------------------------------------\n");
            } else {
                System.out.println("City not found: " + cityName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * MAIN EXECUTION METHOD
     */
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database (Uses 'db:3306' for Docker by default)
        String dbLocation = (args.length < 1) ? "db:3306" : args[0];
        int dbDelay = (args.length < 2) ? 10000 : Integer.parseInt(args[1]);

        a.connect(dbLocation, dbDelay);

        if (a.con != null) {
            System.out.println("\n--- GENERATING MISSING ASSESSMENT REPORTS ---");

            // 1. Language Report (Chinese, English, Hindi, Spanish, Arabic)
            a.reportLanguageStatistics();

            // 2. Population Split Report (Continent)
            a.reportPopulationSplit("Continent", "Asia");

            // 3. Population Split Report (Region)
            a.reportPopulationSplit("Region", "Western Europe");

            // 4. Population Split Report (Country)
            a.reportPopulationSplit("Country", "France");

            // 5. District Report (Top 5 cities in 'California')
            a.reportTopNCitiesInDistrict("California", 5);

            // 6. Single Population Check (Example: 'Edinburgh')
            a.reportSpecificCityPopulation("Edinburgh");
        }

        // Disconnect
        a.disconnect();
    }
}