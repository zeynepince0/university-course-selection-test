# =========================
# 1. Build Stage
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

# Testleri burada çalıştırmıyoruz
RUN mvn clean package -DskipTests=true

# =========================
# 2. Runtime Stage
# =========================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]
