FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/mobile-provider-0.0.1-SNAPSHOT.jar app.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

# Uygulamayı başlat
ENTRYPOINT ["java","-jar","/app.jar"]
