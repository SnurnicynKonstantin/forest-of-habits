FROM gradle:8.0.2-jdk17 AS build

WORKDIR /home/gradle/src

COPY build.gradle settings.gradle gradlew ./

COPY gradle ./gradle

COPY src ./src

RUN ./gradlew build -x test

FROM eclipse-temurin:17

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

EXPOSE 8082

CMD ["java", "-jar", "./app.jar"]