# Stage 1: Build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
RUN ./gradlew --version
COPY web-api ./web-api
RUN ./gradlew build --no-daemon -x test

# Stage 2: Runtime
FROM amazoncorretto:21-alpine-jdk
LABEL authors="joesharpcs.co.uk"

# Install curl
RUN apk --no-cache add curl

COPY --from=builder /app/web-api/build/libs/sports-day-service-*.jar app.jar

HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 CMD curl -f http://localhost:8080/api/actuator/health || exit 1

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
# Set the entrypoint
ENTRYPOINT ["/entrypoint.sh"]
