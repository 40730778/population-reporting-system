# Population Reporting System.

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
- Build and run with Maven or Docker.
.
## Project Structure

population-reports/
├─ src/main/java/com/napier/sem/   # Java source code
├─ target/                         # Compiled JAR (after build)
├─ pom.xml                         # Maven build file
├─ Dockerfile                      # Docker image definition
├─ docker-compose.yml              # Docker Compose for app + DB
├─ docs/                           # Use cases, diagrams, documentation
├─ CODE_OF_CONDUCT.md              # Project Code of Conduct
└─ README.md                       # Project documentation


<!-- Prerequisites -->
- **Java 17**
- **Maven**
- Optional: **Docker**


## Scrum & Workflow

Project managed using Scrum methodology

Product Backlog maintained through GitHub Issues

Tasks defined as User Stories

Use Cases documented for core reporting features

Use Case Diagram included in project documentation

Sprint and Kanban boards managed via Zube.io

Team collaboration tracked through commits, issues, and reviews

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

- ## Report Output Formats
### Country Report
- Code
- Name
- Continent
- Region
- Population
- Capital

### City Report
- Name
- Country
- District
- Population

### Capital City Report
- Name
- Country
- Population

### Population Report
- Name of continent/region/country
- Total population
- Population living in cities (with %)
- Population not living in cities (with %)

## Current Features Implemented
- Country Report (feature/country-report)
- City Report (feature/city-report)
- Capital City Report (feature/capital-city-report)
- Language Statistics (feature/language-stats)
- Population Statistics


## 🧩 Branching Strategy

The project follows the **Git Flow** branching model:

| Branch | Purpose |
|---------|----------|
| `master` | Stable integration branch |
| `feature/country-report` | Country population reports |
| `feature/city-report` | City population reports |
| `feature/capital-city-report` | Capital city reports |
| `feature/population-report` | Population analysis |
| `feature/language-stats-report` | Language-based population statistics |
| `feature/tests` | Unit and integration test development |

Each report was developed and tested in its own branch before merging into `master`.


## 🧱 Continuous Integration and Testing

All builds and tests are automatically verified through **GitHub Actions** using Maven.

### CI Workflow (`.github/workflows/build.yml`)
The workflow:
1. Checks out the repository
2. Sets up **JDK 17**
3. Builds the project with Maven
4. Runs all **JUnit 5 tests**
5. Builds the **Docker image**

This ensures every commit is tested for compilation, functionality, and integration.

### Test Coverage

| Test Type | Description | File |
|------------|--------------|------|
| **Unit Tests** | Validate service methods for each report type (Country, City, Capital City, etc.) | `CountryReportServiceTest.java`, `CityReportServiceTest.java`, etc. |
| **Integration Test** | Confirms database connectivity | `DatabaseIntegrationTest.java` |

All tests execute automatically in GitHub Actions.


## Team Contribution Tracking

Group work completed collaboratively through shared repository

Individual contributions monitored via GitHub metrics

Required contribution spreadsheet completed at each review point

Percentages agreed collectively by team

Peer assessment used to adjust individual weighting if necessary


## 🗄️ Database Configuration

The application connects to the **MySQL `world` database** provided by the lecturer.

Connection settings are stored in `Database.java`:

```java
String host = "localhost";
String database = "world";
String user = "root";
String password = "root";
```



## Code of Conduct
Please see CODE_OF_CONDUCT.md for guidelines on contributing and interacting with the team.

## Deployment

The application is fully deployable using Docker containers. The project uses Docker Compose to orchestrate both the Java application and the MySQL database in isolated containers.

The database is deployed using a MySQL container and is automatically initialised using an SQL script. The application dynamically connects to the database at runtime through environment-based configuration, allowing flexible deployment across different environments.

The deployment process has been successfully demonstrated during Code Review 4, including building Docker images, starting containers, and verifying database connectivity.

A structured bug reporting system is implemented using GitHub Issues, including predefined issue templates to standardise bug reports and improve team collaboration.

## Contributors
Awin Aniyachan

## Contribution Link
https://livenapierac-my.sharepoint.com/:x:/r/personal/40730778_live_napier_ac_uk/Documents/Group%20Contribution.xlsx?d=w1901c9fc02ea40bcac1b2b633fa8e833&e=4%3a3fc914a6571747118bd2bb11cab6e6ea&sharingv2=true&fromShare=true&at=9

.
