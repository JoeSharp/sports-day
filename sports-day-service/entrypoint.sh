#!/bin/sh
# Use the environment variables in the Java command
exec java ${JAVA_OPTS} -jar /app.jar
