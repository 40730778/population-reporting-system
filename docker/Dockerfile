# Use a base image with Java
FROM openjdk:17-jdk-slim

# Create app directory
WORKDIR /app

# Copy jar file from target folder (once built)
COPY target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
