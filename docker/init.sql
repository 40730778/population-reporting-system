CREATE DATABASE population;
USE population;

CREATE TABLE country (
                         code CHAR(3) PRIMARY KEY,
                         name VARCHAR(255),
                         continent VARCHAR(255),
                         region VARCHAR(255),
                         population BIGINT,
                         capital VARCHAR(255)
);

INSERT INTO country (code, name, continent, region, population, capital)
VALUES ('GBR', 'United Kingdom', 'Europe', 'Northern Europe', 67886011, 'London');
