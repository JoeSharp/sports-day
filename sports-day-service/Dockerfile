FROM amazoncorretto:21-alpine-jdk
LABEL authors="joesharpcs.co.uk"

# Install curl
RUN apk --no-cache add curl

COPY build/libs/sports-day-service-0.0.1-SNAPSHOT.jar app.jar

HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 CMD curl -f http://localhost:8080/api/actuator/health || exit 1

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
# Set the entrypoint
ENTRYPOINT ["/entrypoint.sh"]
