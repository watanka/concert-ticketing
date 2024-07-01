# 1. Build Stage
FROM gradle:8.8-jdk17 AS build
LABEL authors="esshi"
# 2. Work Directory 설정
WORKDIR /app

# 3. Gradle 종속성 캐싱
COPY build.gradle settings.gradle ./
RUN gradle build --no-daemon || return 0

# 4. 소스 코드 복사 및 빌드
COPY src ./src
RUN gradle build --no-daemon

# 5. Runtime Stage
FROM openjdk:17-jdk-alpine

# 6. Work Directory 설정
WORKDIR /app

# 7. Build Stage에서 생성된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 8. 포트 설정 (Spring Boot 기본 포트 8080)
EXPOSE 8080

# 9. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

