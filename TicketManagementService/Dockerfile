# -------- Stage 1: Build the application --------
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests

# -------- Stage 2: Run the application --------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV USER_DB=user
ENV PASSWORD_DB=password
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://ticket-db:5432/ticket

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
