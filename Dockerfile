# 1. Use Java 17 base image
FROM openjdk:17

# 2. Copy the built JAR file into the container
COPY population-reports.jar app.jar

# 3. Command to run the JAR when the container starts
ENTRYPOINT ["java","-jar","/app.jar"]
