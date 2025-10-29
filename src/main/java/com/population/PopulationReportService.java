package com.population;

import com.population.Database;
import com.population.model.PopulationStat;
import com.population.util.ReportPrinter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PopulationReportService {

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
                    String.format("%d (%.2f%%)", p.cityPopulation, p.cityPercentage),
                    String.format("%d (%.2f%%)", p.nonCityPopulation, p.nonCityPercentage)
            });
        }
        ReportPrinter.printTable(rows, "Name", "Total Population", "In Cities", "Not In Cities");
    }

    // ======================
    // REPORT 23: Continent
    // ======================
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

    // ======================
    // REPORT 24: Region
    // ======================
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

    // ======================
    // REPORT 25: Country
    // ======================
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
}
