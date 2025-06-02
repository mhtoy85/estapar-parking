FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/parking-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]