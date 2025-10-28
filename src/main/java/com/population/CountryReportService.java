package com.population;

import com.population.Database;
import com.population.model.Country;
import com.population.util.ReportPrinter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryReportService {

    // ✅ Maps one database row to a Country object
    private Country map(ResultSet rs) throws SQLException {
        return new Country(
                rs.getString("Code"),
                rs.getString("Name"),
                rs.getString("Continent"),
                rs.getString("Region"),
                rs.getInt("Population"),
                rs.getString("CapitalName")
        );
    }

    // ✅ Runs an SQL query and returns a list of Country objects
    private List<Country> run(String sql, Object... params) {
        List<Country> list = new ArrayList<>();
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Dynamically fill ? parameters
            for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Query failed: " + e.getMessage());
        }
        return list;
    }

    // ✅ Prints all Country objects neatly
    private void print(List<Country> items) {
        List<String[]> rows = new ArrayList<>();
        for (Country c : items) {
            rows.add(new String[]{
                    c.code, c.name, c.continent, c.region,
                    String.valueOf(c.population), c.capitalName
            });
        }
        ReportPrinter.printTable(rows, "Code", "Name", "Continent", "Region", "Population", "Capital");
    }

    // ✅ Report 1: All countries by population
    public void listAllCountries() {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            ORDER BY c.Population DESC
        """;
        print(run(sql));
    }

    // ✅ Report 2: Countries by continent
    public void listCountriesByContinent(String continent) {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            WHERE c.Continent = ?
            ORDER BY c.Population DESC
        """;
        print(run(sql, continent));
    }

    // ✅ Report 3: Countries by region
    public void listCountriesByRegion(String region) {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            WHERE c.Region = ?
            ORDER BY c.Population DESC
        """;
        print(run(sql, region));
    }

    // ✅ Report 4: Top N countries in the world by population
    public void topNCountriesInWorld(int n) {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            ORDER BY c.Population DESC
            LIMIT ?
        """;
        print(run(sql, n));
    }

    // ✅ Report 5: Top N countries in a specific continent
    public void topNCountriesInContinent(String continent, int n) {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            WHERE c.Continent = ?
            ORDER BY c.Population DESC
            LIMIT ?
        """;
        print(run(sql, continent, n));
    }

    // ✅ Report 6: Top N countries in a specific region
    public void topNCountriesInRegion(String region, int n) {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            WHERE c.Region = ?
            ORDER BY c.Population DESC
            LIMIT ?
        """;
        print(run(sql, region, n));
    }
}
