# Step 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Only copy what's necessary for build (so Docker cache works better)
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/tradeo-0.0.1-SNAPSHOT.jar tradeo.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "tradeo.jar"]
