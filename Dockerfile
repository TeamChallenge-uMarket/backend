FROM openjdk:17
WORKDIR /app
COPY target/*.jar uMarketPrj-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/uMarketPrj-0.0.1-SNAPSHOT.jar"]