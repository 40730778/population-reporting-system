package com.napier.sem;

public class CountryReport {
    private String code;
    private String name;
    private String continent;
    private String region;
    private int population;
    private String capital;

    public CountryReport(String code, String name, String continent, String region, int population, String capital) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
    }

    public int getPopulation() {
        return population;
    }

    public String getName() { return name; }
    // other getters...
}
