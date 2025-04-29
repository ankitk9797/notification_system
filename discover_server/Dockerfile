# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy source code and pom.xml
COPY pom.xml .
COPY src ./src

# Build the Spring Boot app (with dependencies)
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal image with only the built app
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your service runs on (update if different)
EXPOSE 8761

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
