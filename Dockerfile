# 1. Aşama: Build (Hem Java hem Chrome olan özel bir imaj kullanıyoruz)
# Bu sayede testler Docker içinde çalışırken "Chrome bulunamadı" hatası almayacak.
FROM markhobson/maven-chrome:jdk-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Projeyi derle ve testleri çalıştırarak paketi oluştur
RUN mvn clean package -DskipTests=false

# 2. Aşama: Çalıştırma (Run)
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]