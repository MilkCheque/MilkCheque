# Use official JDK 21 image
FROM openjdk:21-jdk-slim

# Use working directory
WORKDIR /MilkCheque

# Copy the JAR file
COPY target/*.jar MilkCheque.jar

# Exposing the port
EXPOSE 8080

# Starting the application
ENTRYPOINT ["java", "-jar", "MilkCheque.jar"]
