package com.population;

import com.population.Database;
import com.population.model.City;
import com.population.util.ReportPrinter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityReportService {

    // ✅ Convert a database row into a City object
    private City map(ResultSet rs) throws SQLException {
        return new City(
                rs.getString("CityName"),
                rs.getString("CountryName"),
                rs.getString("District"),
                rs.getInt("Population")
        );
    }

    // ✅ Run a query and return a list of City objects
    private List<City> run(String sql, Object... params) {
        List<City> list = new ArrayList<>();
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

    // ✅ Print formatted city tables
    private void print(List<City> items) {
        List<String[]> rows = new ArrayList<>();
        for (City c : items) {
            rows.add(new String[]{
                    c.name, c.country, c.district, String.valueOf(c.population)
            });
        }
        ReportPrinter.printTable(rows, "City", "Country", "District", "Population");
    }

    // ✅ Report 7: All cities in the world by population
    public void listAllCities() {
        String sql = """
            SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            ORDER BY ci.Population DESC
        """;
        print(run(sql));
    }

    // ✅ Report 8: All cities in a specific continent by population
    public void listCitiesByContinent(String continent) {
        String sql = """
            SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC
        """;
        print(run(sql, continent));
    }

    // ✅ Report 9: All cities in a specific region by population
    public void listCitiesByRegion(String region) {
        String sql = """
            SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Region = ?
            ORDER BY ci.Population DESC
        """;
        print(run(sql, region));
    }

    // ✅ Report 10: All cities in a specific country by population
    public void listCitiesByCountry(String country) {
        String sql = """
            SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Name = ?
            ORDER BY ci.Population DESC
        """;
        print(run(sql, country));
    }

    // ✅ Report 11: All cities in a specific district by population
    public void listCitiesByDistrict(String district) {
        String sql = """
            SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE ci.District = ?
            ORDER BY ci.Population DESC
        """;
        print(run(sql, district));
    }

    // ✅ Report 12: Top N most populated cities in the world
    public void topNCitiesInWorld(int n) {
        String sql = """
            SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            ORDER BY ci.Population DESC
            LIMIT ?
        """;
        print(run(sql, n));
    }

    // ✅ Report 13: Top N most populated cities in a specific continent
    public void topNCitiesInContinent(String continent, int n) {
        String sql = """
            SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC
            LIMIT ?
        """;
        print(run(sql, continent, n));
    }

    // ✅ Report 14: Top N most populated cities in a specific region
    public void topNCitiesInRegion(String region, int n) {
        String sql = """
            SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Region = ?
            ORDER BY ci.Population DESC
            LIMIT ?
        """;
        print(run(sql, region, n));
    }

    // ✅ Report 15: Top N most populated cities in a specific country
    public void topNCitiesInCountry(String country, int n) {
        String sql = """
            SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population
            FROM city ci
            JOIN country co ON ci.CountryCode = co.Code
            WHERE co.Name = ?
            ORDER BY ci.Population DESC
            LIMIT ?
        """;
        print(run(sql, country, n));
    }
}
