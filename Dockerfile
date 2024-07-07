# Build stage
FROM gradle:7.6.0-jdk17 AS build
ARG PROFILE=${PROFILE}
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY src /app/src
RUN gradle build --no-daemon -x test

# Run stage
FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
ARG PROFILE=${PROFILE}
COPY --from=build /app/src/main/resources/application-${PROFILE}.yml /src/main/resources/application-${PROFILE}.yml
COPY --from=build /app/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]