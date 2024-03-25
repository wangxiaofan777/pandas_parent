FROM openjdk:8-jdk-alpine
LABEL authors="wangmaosong"

WORKDIR /app

COPY pandas-auth/target/pandas-auth-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]