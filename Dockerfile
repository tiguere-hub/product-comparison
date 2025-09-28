#Build Stage
FROM gradle:jdk21 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY gradlew ./
COPY gradle ./gradle
COPY src ./src
RUN chmod +x gradlew && ./gradlew clean build --no-daemon -s


#Runtime Stage
FROM openjdk:21-slim-bullseye
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]