FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .

RUN mvn -q -ntp -DskipTests clean package -pl catalog-deck-service -am

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /app/catalog-deck-service/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
