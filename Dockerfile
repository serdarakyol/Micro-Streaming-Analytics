# Stage 1: Build stage
FROM maven:3.8.3-openjdk-21 AS builder

WORKDIR /app

# Copy the Maven configuration
COPY pom.xml .

# Download dependencies
RUN mvn -B dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn -B clean package

# Stage 2: Run stage
FROM openjdk:21-jre-slim

WORKDIR /app

# Copy the JAR file built in the previous stage
COPY --from=builder /app/target/your-application.jar .

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "your-application.jar"]
