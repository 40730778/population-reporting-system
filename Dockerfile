From openjdk:17
COPY ./target/*-jar-with-dependencies.jar /tmp/app.jar
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "app.jar"]