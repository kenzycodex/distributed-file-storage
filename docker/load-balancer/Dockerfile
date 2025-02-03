# Multi-stage build for optimized image
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
LABEL maintainer="CloudStore Engineering Team"
LABEL version="1.0.0"
LABEL description="CloudStore Load Balancer Container"

# Create non-root user
RUN addgroup --system cloudstore && adduser --system --ingroup cloudstore cloudstore
USER cloudstore

WORKDIR /app

# Copy compiled artifact from builder stage
COPY --from=builder /app/target/*.jar load-balancer.jar

# Application configuration
ENV JAVA_OPTS="-XX:+UseContainerAwareness -XX:MaxRAMPercentage=75.0"
ENV SPRING_PROFILES_ACTIVE=production

# Expose load balancer port
EXPOSE 8080

# Sophisticated health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget -q -O- http://localhost:8080/actuator/health || exit 1

# Entrypoint with dynamic JVM configuration
ENTRYPOINT exec java $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
    -jar /app/load-balancer.jar