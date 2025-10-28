package com.population.model;

/**
 * Represents a Capital City record from the database.
 * Used in Reports 16–22.
 */
public class CapitalCity {
    public final String name;       // Capital city name
    public final String country;    // Country the capital belongs to
    public final int population;    // Capital city population

    public CapitalCity(String name, String country, int population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }
}
