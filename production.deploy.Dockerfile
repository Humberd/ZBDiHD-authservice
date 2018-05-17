# this Dockerfile is responsible for hosting api
FROM openjdk:8-jdk-alpine
COPY target/zbdihd-auth-service*.jar zbdihd-auth-service.jar

ENV JAVA_OPTS="-Dspring.profiles.active=production"

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar zbdihd-auth-service.jar