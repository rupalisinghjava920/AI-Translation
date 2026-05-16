#FROM maven:3.9.6-eclipse-temurin-17
#
#WORKDIR /app
#
#COPY target/ai-translation-0.0.1-SNAPSHOT.jar app.jar
#
#RUN mvn clean package -DskipTests
#
#CMD ["java", "-jar", "app.jar"]

FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]