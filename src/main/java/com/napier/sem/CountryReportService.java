package com.populationreporting;

import com.populationreporting.dao.CountryDAO;
import com.populationreporting.model.Country;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CountryReport {
    private final CountryDAO countryDAO;

    public CountryReport(Connection connection) {
        this.countryDAO = new CountryDAO(connection);
    }

    public void printTopNCountriesByPopulation() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of top populated countries to display (N): ");
            int n = scanner.nextInt();

            List<Country> countries = countryDAO.getTopNCountriesByPopulation(n);

            System.out.println("\n=== Top " + n + " Populated Countries in the World ===");
            System.out.printf("%-5s %-35s %-15s %-25s %-15s %-10s%n", 
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------");

            for (Country country : countries) {
                System.out.printf("%-5s %-35s %-15s %-25s %-15d %-10s%n",
                        country.getCode(),
                        country.getName(),
                        country.getContinent(),
                        country.getRegion(),
                        country.getPopulation(),
                        country.getCapital());
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving top populated countries: " + e.getMessage());
        }
    }
}
