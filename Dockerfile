# 1. Aşama: Build (Lokalde Maven hızlandırmak için)
FROM markhobson/maven-chrome:jdk-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Testleri burada koşturmuyoruz, Jenkins içindeki stage'lerde koşturacağız
RUN mvn clean package -DskipTests=true

# 2. Aşama: Çalıştırma
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]