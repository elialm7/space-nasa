# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src

# Variables de entorno desde build args
ARG GITHUB_USER
ARG GITHUB_TOKEN
ENV GITHUB_USER=$GITHUB_USER
ENV GITHUB_TOKEN=$GITHUB_TOKEN

# Crear settings.xml correctamente escapando saltos de línea
RUN mkdir -p /root/.m2 && \
    echo "<settings xmlns='http://maven.apache.org/SETTINGS/1.0.0'>\
<servers>\
<server>\
<id>github</id>\
<username>\${env.GITHUB_USER}</username>\
<password>\${env.GITHUB_TOKEN}</password>\
</server>\
</servers>\
</settings>" > /root/.m2/settings.xml

RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

RUN groupadd -r spring && useradd -r -g spring spring
USER spring

EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
