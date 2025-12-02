# -------- Stage 1: Build the application --------
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests

# -------- Stage 2: Run the application --------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/GatewayService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]