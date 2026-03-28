FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/Smart-Parking-System-API-0.0.1-SNAPSHOT.jar ParkingSystem.jar
ENTRYPOINT ["java","-jar","ParkingSystem.jar"]
