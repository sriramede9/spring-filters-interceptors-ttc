# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy only the built jar
COPY --from=build /app/target/*.jar app.jar

# Expose the TTC Service Port
EXPOSE 8080

# Run with optimized memory settings for container
ENTRYPOINT ["java", "-jar", "app.jar"]