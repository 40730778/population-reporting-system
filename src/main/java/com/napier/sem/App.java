package com.napier.sem;

import java.sql.*;

public class App {
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     * @param location The database location (e.g., "db:3306" for Docker or "localhost:33060" for local)
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
                    double percent = ((double)speakers / worldPop) * 100;
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
     * REQUIREMENT: Top N Cities in a District
     */
    public void reportTopNCitiesInDistrict(String district, int n) {
        try {
            Statement stmt = con.createStatement();
            // JOIN with country to get the Country Name instead of Code (Req: Name, Country, District, Pop)
            String strSelect = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
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
                        rset.getInt("Population")