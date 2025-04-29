# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only the pom.xml to cache dependencies
COPY pom.xml .

# Download dependencies (this will be cached if pom.xml is not changed)
RUN mvn dependency:go-offline -B

# Now copy the rest of the source code
COPY src ./src

# Build the Spring Boot app (with dependencies)
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal image with only the built app
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your service runs on (update if different)
EXPOSE 9092

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
