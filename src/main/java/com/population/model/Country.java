package com.population.model;

public class Country {
    public String code;
    public String name;
    public String continent;
    public String region;
    public int population;
    public String capitalName;

    public Country(String code, String name, String continent, String region, int population, String capitalName) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capitalName = capitalName;
    }
}
