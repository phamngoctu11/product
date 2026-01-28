# B1: Dùng Maven với Java 21 để build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# B2: Chạy file jar bằng Java 21 (Amazon Corretto)
FROM amazoncorretto:21-alpine-jdk
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]