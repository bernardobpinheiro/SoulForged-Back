FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o projeto inteiro (necessário para monorepo Maven)
COPY . .

# Compila SOMENTE esse módulo
RUN mvn -q -ntp -DskipTests clean package -pl user-collection-service -am

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /app/user-collection-service/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
