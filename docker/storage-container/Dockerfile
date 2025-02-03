# Multi-stage build for optimized image
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
LABEL maintainer="CloudStore Engineering Team"
LABEL version="1.0.0"
LABEL description="CloudStore Storage Container"

# Create non-root user with specific permissions
RUN addgroup --system cloudstore && \
    adduser --system --ingroup cloudstore cloudstore && \
    mkdir -p /data/files && \
    chown -R cloudstore:cloudstore /data

USER cloudstore
WORKDIR /app

# Copy compiled artifact from builder stage
COPY --from=builder /app/target/*.jar storage-container.jar

# Volume for persistent file storage
VOLUME /data/files

# Application configuration
ENV JAVA_OPTS="-XX:+UseContainerAwareness -XX:MaxRAMPercentage=75.0"
ENV SPRING_PROFILES_ACTIVE=production
ENV STORAGE_BASE_DIR=/data/files

# Expose storage container port
EXPOSE 9090

# Sophisticated health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget -q -O- http://localhost:9090/actuator/health || exit 1

# Entrypoint with dynamic JVM configuration
ENTRYPOINT exec java $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
    -jar /app/storage-container.jar