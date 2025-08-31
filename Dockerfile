# Build the app
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run the app
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar MilkCheque.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "MilkCheque.jar"]
