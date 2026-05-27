FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests

# 5. Ejecutamos la app
CMD ["java", "-jar", "target/AlAngulo-0.0.1-SNAPSHOT.jar"]