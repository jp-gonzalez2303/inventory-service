FROM gradle:8.4-jdk17-alpine AS build

WORKDIR /app
COPY . .

RUN gradle dependencies --no-daemon

RUN gradle bootJar --no-daemon -x test

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
