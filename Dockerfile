FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar ParkingSystem.jar
ENTRYPOINT ["java","-jar","ParkingSystem.jar"]
