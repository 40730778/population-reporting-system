# 1. Use Java 17 base image
FROM amazoncorretto:17

# 2. Copy the built JAR file into the container
COPY target/population-reports-1.0-SNAPSHOT.jar app.jar

# 3. Command to run the JAR when the container starts
ENTRYPOINT ["java", "-jar", "/app.jar"]
