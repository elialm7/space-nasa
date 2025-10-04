# Build stage
FROM maven:3.9.6-openjdk-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the JAR from build stage
COPY --from=builder /app/target/*.jar app.jar

# Create a non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

# Expose port
EXPOSE 5000

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
