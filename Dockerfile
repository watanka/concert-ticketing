# Build stage
FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY src /app/src
RUN gradle build --no-daemon -x test

# Run stage
FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY --from=build /app/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]