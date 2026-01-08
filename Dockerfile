FROM markhobson/maven-chrome:jdk-21

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

# target container içinde oluşacak
RUN mvn -q -DskipTests dependency:go-offline
