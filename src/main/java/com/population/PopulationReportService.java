package com.population;

import com.population.Database;
import com.population.model.PopulationStat;
import com.population.util.ReportPrinter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles Population Reports (#23–28)
 * - 23: Continent population breakdown
 * - 24: Region population breakdown
 * - 25: Country population breakdown
 * - 26: World population total
 * - 27: Continent population total
 * - 28: Region population total
 *
 * Author: Israel Ayemo
 * Matric: 40730778
 */
public class PopulationReportService {

    // =========================================================
    // Utility methods used across all population reports
    // =========================================================
    private PopulationStat map(ResultSet rs) throws SQLException {
        long total = rs.getLong("TotalPopulation");
        long city = rs.getLong("CityPopulation");
        long nonCity = total - city;
        return new PopulationStat(rs.getString("Name"), total, city, nonCity);
    }

    private List<PopulationStat> run(String sql) {
        List<PopulationStat> list = new ArrayList<>();
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            System.out.println("❌ Query failed: " + e.getMessage());
        }
        return list;
    }

    private void print(List<PopulationStat> items) {
        List<String[]> rows = new ArrayList<>();
        for (PopulationStat p : items) {
            rows.add(new String[]{
                    p.name,
                    String.valueOf(p.totalPopulation),
                    String.format("%,d (%.2f%%)", p.cityPopulation, p.cityPercentage),
                    String.format("%,d (%.2f%%)", p.nonCityPopulation, p.nonCityPercentage)
            });
        }
        ReportPrinter.printTable(rows, "Name", "Total Population", "In Cities", "Not In Cities");
    }

    // =========================================================
    // REPORT 23: Population by Continent
    // =========================================================
    public void populationByContinent() {
        String sql = """
            SELECT c.Continent AS Name,
                   SUM(c.Population) AS TotalPopulation,
                   SUM(ci.Population) AS CityPopulation
            FROM country c
            LEFT JOIN city ci ON ci.CountryCode = c.Code
            GROUP BY c.Continent
            ORDER BY TotalPopulation DESC;
        """;
        print(run(sql));
    }

    // =========================================================
    // REPORT 24: Population by Region
    // =========================================================
    public void populationByRegion() {
        String sql = """
            SELECT c.Region AS Name,
                   SUM(c.Population) AS TotalPopulation,
                   SUM(ci.Population) AS CityPopulation
            FROM country c
            LEFT JOIN city ci ON ci.CountryCode = c.Code
            GROUP BY c.Region
            ORDER BY TotalPopulation DESC;
        """;
        print(run(sql));
    }

    // =========================================================
    // REPORT 25: Population by Country
    // =========================================================
    public void populationByCountry() {
        String sql = """
            SELECT c.Name AS Name,
                   c.Population AS TotalPopulation,
                   SUM(ci.Population) AS CityPopulation
            FROM country c
            LEFT JOIN city ci ON ci.CountryCode = c.Code
            GROUP BY c.Code
            ORDER BY TotalPopulation DESC;
        """;
        print(run(sql));
    }

    // =========================================================
    // REPORT 26: Total Population of the World
    // =========================================================
    public void populationOfWorld() {
        String sql = """
            SELECT SUM(Population) AS Population
            FROM country;
        """;
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                System.out.printf("\n🌍 World Population: %,d%n", rs.getLong("Population"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to get world population: " + e.getMessage());
        }
    }

    // =========================================================
    // REPORT 27: Total Population of a Continent
    // =========================================================
    public void populationOfContinent(String continent) {
        String sql = """
            SELECT SUM(Population) AS Population
            FROM country
            WHERE Continent = ?;
        """;
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, continent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.printf("🌎 Population of %s: %,d%n",
                            continent, rs.getLong("Population"));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to get continent population: " + e.getMessage());
        }
    }

    // =========================================================
    // REPORT 28: Total Population of a Region
    // =========================================================
    public void populationOfRegion(String region) {
        String sql = """
            SELECT SUM(Population) AS Population
            FROM country
            WHERE Region = ?;
        """;
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, region);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.printf("🌍 Population of %s Region: %,d%n",
                            region, rs.getLong("Population"));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to get region population: " + e.getMessage());
        }
    }
}
