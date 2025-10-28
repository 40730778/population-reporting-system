package com.population;

import com.population.Database;
import com.population.model.CapitalCity;
import com.population.util.ReportPrinter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CapitalCityReportService {

    // ✅ Map each row to a CapitalCity object
    private CapitalCity map(ResultSet rs) throws SQLException {
        return new CapitalCity(
                rs.getString("CapitalName"),
                rs.getString("CountryName"),
                rs.getInt("Population")
        );
    }

    // ✅ Run SQL query and return list
    private List<CapitalCity> run(String sql, Object... params) {
        List<CapitalCity> list = new ArrayList<>();
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Query failed: " + e.getMessage());
        }
        return list;
    }

    // ✅ Print formatted table
    private void print(List<CapitalCity> items) {
        List<String[]> rows = new ArrayList<>();
        for (CapitalCity c : items) {
            rows.add(new String[]{c.name, c.country, String.valueOf(c.population)});
        }
        ReportPrinter.printTable(rows, "Capital City", "Country", "Population");
    }

    // ===============================================================
    // 🏛️ REPORT 16: All Capital Cities in the World
    // ===============================================================
    public void listAllCapitalCities() {
        String sql = """
            SELECT ci.Name AS CapitalName, co.Name AS CountryName, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            ORDER BY ci.Population DESC
        """;
        print(run(sql));
    }

    // ===============================================================
    // 🏛️ REPORT 17: All Capital Cities in a Continent
    // ===============================================================
    public void listCapitalCitiesByContinent(String continent) {
        String sql = """
            SELECT ci.Name AS CapitalName, co.Name AS CountryName, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC
        """;
        print(run(sql, continent));
    }

    // ===============================================================
    // 🏛️ REPORT 18: All Capital Cities in a Region
    // ===============================================================
    public void listCapitalCitiesByRegion(String region) {
        String sql = """
            SELECT ci.Name AS CapitalName, co.Name AS CountryName, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            WHERE co.Region = ?
            ORDER BY ci.Population DESC
        """;
        print(run(sql, region));
    }

    // ===============================================================
    // 🏛️ REPORT 19: Top N Most Populated Capital Cities in the World
    // ===============================================================
    public void topNCapitalCitiesInWorld(int n) {
        String sql = """
            SELECT ci.Name AS CapitalName, co.Name AS CountryName, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            ORDER BY ci.Population DESC
            LIMIT ?
        """;
        print(run(sql, n));
    }

    // ===============================================================
    // 🏛️ REPORT 20: Top N Most Populated Capital Cities in a Continent
    // ===============================================================
    public void topNCapitalCitiesInContinent(String continent, int n) {
        String sql = """
            SELECT ci.Name AS CapitalName, co.Name AS CountryName, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC
            LIMIT ?
        """;
        print(run(sql, continent, n));
    }

    // ===============================================================
    // 🏛️ REPORT 21: Top N Most Populated Capital Cities in a Region
    // ===============================================================
    public void topNCapitalCitiesInRegion(String region, int n) {
        String sql = """
            SELECT ci.Name AS CapitalName, co.Name AS CountryName, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            WHERE co.Region = ?
            ORDER BY ci.Population DESC
            LIMIT ?
        """;
        print(run(sql, region, n));
    }

    // ===============================================================
    // 🏛️ REPORT 22: Top N Most Populated Capital Cities in a Country
    // ===============================================================
    public void topNCapitalCitiesInCountry(String country, int n) {
        String sql = """
            SELECT ci.Name AS CapitalName, co.Name AS CountryName, ci.Population
            FROM city ci
            JOIN country co ON ci.ID = co.Capital
            WHERE co.Name = ?
            ORDER BY ci.Population DESC
            LIMIT ?
        """;
        print(run(sql, country, n));
    }
}
