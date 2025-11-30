FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .

RUN mvn -q -ntp -DskipTests clean package -pl duel-service -am

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /app/duel-service/target/*.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
