Hostel Booking Backend

A **microservices-based backend system** for a hostel booking platform built using **Spring Boot**, **Spring Cloud**, and **Netflix Eureka**.  
The system supports user authentication, hostel management, room booking, and payments.

-
Architecture Overview

The backend follows a **microservices architecture** with an **API Gateway** and **Service Discovery**.


---

 Microservices

| Service Name       | Description                          | Port |
|--------------------|--------------------------------------|------|
| Eureka Server      | Service discovery                    |      |
| API Gateway        | Routing & CORS handling              |      |
| User Service       | Authentication & user management     |      |
| Hostel Service     | Hostel & room management             | ---- |
| Booking Service    | Room booking logic                   | ---- |
| Payment Service    | Payment handling                     | ---- |

---

 Technologies Used

- Java 21
- Spring Boot
- Spring Cloud Gateway
- Netflix Eureka
- Spring Data JPA
- Hibernate
- MySQL
- Maven

- Git & GitHub

---

 Features

- User registration & login
- Role-based access (STUDENT, LANDLORD, ADMIN)
- Hostel and room listings
- Booking management
- Payment processing
- Centralized routing via API Gateway
- Service discovery with Eureka

---

 How to Run the Project

 Start Eureka Server
```bash
cd eureka-server
mvn spring-boot:run

cd api-gateway
mvn spring-boot:run

cd user_service
mvn spring-boot:run

Project structure
hostelBackend/
│
├── api-gateway/
├── eureka-server/
├── user_service/
├── booking_service/
├── room_service/
├── payment_service/
└── README.md




