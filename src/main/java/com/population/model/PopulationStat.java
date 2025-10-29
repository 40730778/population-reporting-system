package com.population.model;

public class PopulationStat {
    public final String name;           // Continent, Region, or Country name
    public final long totalPopulation;  // Total population
    public final long cityPopulation;   // People living in cities
    public final long nonCityPopulation; // People not living in cities
    public final double cityPercentage;  // % living in cities
    public final double nonCityPercentage; // % not living in cities

    public PopulationStat(String name, long totalPopulation, long cityPopulation, long nonCityPopulation) {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = cityPopulation;
        this.nonCityPopulation = nonCityPopulation;

        // Prevent division by zero
        if (totalPopulation > 0) {
            this.cityPercentage = (cityPopulation * 100.0) / totalPopulation;
            this.nonCityPercentage = (nonCityPopulation * 100.0) / totalPopulation;
        } else {
            this.cityPercentage = 0;
            this.nonCityPercentage = 0;
        }
    }
}
