FROM markhobson/maven-chrome:jdk-21

WORKDIR /workspace

# Kaynak kodu al
COPY pom.xml .
COPY src ./src

# Bağımlılıkları cache'le
RUN mvn -q -DskipTests dependency:go-offline

# Varsayılan komut yok — Jenkins ne isterse onu çalıştırır
