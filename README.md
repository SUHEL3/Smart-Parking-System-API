# SmartPark – Parking Management Backend

SmartPark is a backend REST API built using Spring Boot that manages parking operations.  
It allows users to create parking slots, handle vehicle entry and exit, and track slot availability.

---

## Features

- Add and manage parking slots
- Vehicle entry and exit handling
- Automatic slot allocation
- Ticket generation
- View available and occupied slots
- Basic validation and exception handling

---

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- MySQL (or PostgreSQL)
- Maven

---

## Project Structure

src/
- controller – API endpoints  
- service – Business logic  
- repository – Database layer  
- entity – Database models  
- dto – Data transfer objects  
- exception – Error handling  

---

## How to Run

1. Clone the repository
git clone https://github.com/SUHEL3/Smart-Parking-System-API.git

cd smartpark


2. Configure database in `application.properties`
spring.datasource.url=jdbc:mysql://localhost:3306/smartpark
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

3. Run the application
mvn spring-boot:run

The server will start at:
http://localhost:8080


---

## Sample API Endpoints

- POST /api/slots – Create parking slot  
- GET /api/slots – Get all slots  
- POST /api/vehicles/entry – Vehicle entry  
- POST /api/vehicles/exit – Vehicle exit  

---

## Future Improvements

- Add authentication (JWT)
- Docker deployment
- Cloud deployment
- Add unit and integration tests

---

This project was built for learning backend development using Spring Boot.
