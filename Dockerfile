FROM maven:3.8.7-openjdk-18-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package

FROM openjdk:24-slim-bookworm
WORKDIR /app
COPY --from=build /app/target/JMusicBot-Snapshot-All.jar .
COPY config.txt .
COPY serversettings.txt .
EXPOSE 8080
ENTRYPOINT ["java", "-Dnogui=true", "-jar", "JMusicBot-Snapshot-All.jar"]

