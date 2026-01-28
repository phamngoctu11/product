# B1: Dùng Maven để build file jar
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# B2: Chạy file jar (Sử dụng Amazon Corretto thay vì openjdk cũ)
FROM amazoncorretto:17-alpine-jdk
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]