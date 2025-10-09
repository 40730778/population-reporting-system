# Population Reporting System

## Overview
This project is a **Population Reporting System** developed as part of the **SET08103 Software Engineering Methods** module. It allows users to generate reports on populations of countries, cities, districts, and languages using an SQL database. The system is implemented in **Java 17**, packaged with Maven, and optionally can be run using **Docker**.

## Features
- Generate country, city, and capital city reports:
  - Sort by population (largest → smallest)
  - Top N populated countries/cities/capitals
- Population statistics:
  - By continent, region, country, and district
  - People living in cities vs not living in cities (with percentages)
- Language statistics:
  - Number of people speaking Chinese, English, Hindi, Spanish, Arabic (with % of world population)
- Build and run with Maven or Docker

## Project Structure
population-reports/
│
├─ src/main/java/com/napier/sem/ # Java source code
├─ target/ # Compiled JAR file (after build)
├─ pom.xml # Maven build file
├─ Dockerfile # Docker image definition
├─ CODE_OF_CONDUCT.md # Project Code of Conduct
└─ README.md # Project documentation

php-template
Copy code

<!-- Prerequisites -->
- **Java 17**
- **Maven**
- Optional: **Docker**

## Build Instructions
### Using Maven
1. Clone the repository:
```bash
git clone https://github.com/40730778/population-reporting-system.git
cd population-reporting-system
```
2. Build the project:
```bash
mvn clean package
```
3. The JAR file will be generated in:
target/population-reports-1.0-SNAPSHOT.jar.


### Docker
1.  Build the Docker image:
```bash
docker build -t population-reports .
```
2. Run the Docker container:
```bash
docker run --rm population-reports
```

## Usage
Run the JAR file:
```bash
java -jar target/population-reports-1.0-SNAPSHOT.jar
```
Follow the prompts to generate reports by country, city, or language statistics.

### Code of Conduct
Please see CODE_OF_CONDUCT.md for guidelines on contributing and interacting with the team.

### Contributors
Dara Adekoya

Israel Ayemo

Ahmed Ismail
