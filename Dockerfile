# Stage 1: Build stage
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the Maven configuration
COPY pom.xml .
COPY msa-dev .

# Copy the application source code
COPY src ./src

# Run the script to generate environment variables
RUN ./msa-dev generate-env-variable

# Build the application
RUN mvn -B clean package

# Stage 2: Run stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the JAR file built in the previous stage
COPY --from=builder /app/target/MSA-0.0.1-SNAPSHOT.jar .

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application after waiting for MongoDB and RabbitMQ
CMD ["./msa-dev wait-mongodb", "mongodb:27017", "--", "./msa-dev wait-rabbitmq", "rabbitmq:5672", "--", "java", "-jar", "MSA-0.0.1-SNAPSHOT.jar"]
