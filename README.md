# 🏥 SmartCare – Hospital Management System

> A backend-based Hospital Management System developed using **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL**, providing RESTful APIs for managing patients, doctors, appointments, admissions, rooms, laboratory tests, treatments, billing, and payments.

---

## 📌 Overview

**SmartCare** is a Hospital Management System designed to manage and organize essential hospital operations through a structured REST API.

The project follows a **layered architecture** with separate Controller, Service, Repository, DTO, Entity, and Exception Handling layers.

The system demonstrates practical implementation of:

* Object-Oriented Programming (OOP)
* RESTful API development
* CRUD operations
* Database relationships
* Spring Data JPA
* DTO-based data transfer
* Validation
* Custom exception handling
* Global exception handling
* Business rule validation
* Interface-based polymorphism
* Payment processing strategies
* Unit testing

---

## ✨ Features

### 👤 Patient Management

* Create patients
* Retrieve all patients
* Retrieve a patient by ID
* Update patient information
* Delete patients

### 👨‍⚕️ Doctor Management

* Create doctors
* Retrieve doctors
* Retrieve doctor by ID
* Search doctors by specialization
* Update doctor information
* Delete doctors
* Assign doctors to departments

### 📅 Appointment Management

* Create appointments
* Retrieve appointments
* Retrieve appointments by patient
* Retrieve appointments by doctor
* Update appointment details
* Delete appointments
* Appointment status validation

### 🏨 Admission Management

* Create admissions
* Retrieve admissions
* Retrieve admission by ID
* Retrieve admissions by patient
* Update admissions
* Delete admissions
* Room availability validation

### 🚪 Room Management

* Create rooms
* Retrieve rooms
* Retrieve room by ID
* Search rooms by category
* Search rooms by availability
* Update rooms
* Delete rooms

### 🧪 Laboratory Management

* Create laboratory tests
* Retrieve laboratory tests
* Retrieve laboratory test by ID
* Retrieve tests by patient
* Retrieve tests by doctor
* Update laboratory tests
* Delete laboratory tests

### 💊 Treatment Records

* Create treatment records
* Retrieve treatment records
* Retrieve records by patient
* Retrieve records by doctor
* Update treatment records
* Delete treatment records

### 💳 Billing & Payments

* Create bills
* Retrieve bills
* Retrieve bills by patient
* Update bills
* Delete bills
* Support multiple payment methods
* Payment processing through a common payment interface

### ⚠️ Exception Handling

The system includes centralized exception handling using:

`@RestControllerAdvice`

Custom exceptions include:

* `ResourceNotFoundException`
* `DuplicateResourceException`
* `BusinessRuleException`
* `RoomNotAvailableException`
* `InvalidAppointmentStatusException`

Validation errors are also handled centrally.

---

## 🧱 Project Architecture

The application follows a layered architecture:

```text
                    ┌──────────────────────┐
                    │       Client         │
                    │ Postman / Frontend   │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │     Controllers      │
                    │   REST API Layer     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │       Services       │
                    │  Business Logic      │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │     Repositories     │
                    │    Data Access       │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │        MySQL         │
                    │       Database       │
                    └──────────────────────┘
```

---

## 📂 Project Structure

```text
smartcare/
│
├── src/
│   ├── main/
│   │   ├── java/com.smartcare/
│   │   │
│   │   ├── controller/
│   │   │   ├── AdmissionController.java
│   │   │   ├── AppointmentController.java
│   │   │   ├── BillController.java
│   │   │   ├── DepartmentController.java
│   │   │   ├── DoctorController.java
│   │   │   ├── LabTestController.java
│   │   │   ├── PatientController.java
│   │   │   ├── RoomController.java
│   │   │   └── TreatmentRecordController.java
│   │   │
│   │   ├── dto/
│   │   │   ├── AdmissionRequestDto.java
│   │   │   ├── AdmissionResponseDto.java
│   │   │   ├── AppointmentRequestDto.java
│   │   │   ├── AppointmentResponseDto.java
│   │   │   ├── BillRequestDto.java
│   │   │   ├── BillResponseDto.java
│   │   │   ├── DepartmentRequestDto.java
│   │   │   ├── DepartmentResponseDto.java
│   │   │   ├── DoctorRequestDto.java
│   │   │   ├── DoctorResponseDto.java
│   │   │   ├── LabTestRequestDto.java
│   │   │   ├── LabTestResponseDto.java
│   │   │   ├── PatientRequestDto.java
│   │   │   ├── PatientResponseDto.java
│   │   │   ├── PaymentResultDto.java
│   │   │   ├── RoomRequestDto.java
│   │   │   ├── RoomResponseDto.java
│   │   │   ├── TreatmentRecordRequestDto.java
│   │   │   └── TreatmentRecordResponseDto.java
│   │   │
│   │   ├── entity/
│   │   │   ├── Admission.java
│   │   │   ├── Appointment.java
│   │   │   ├── Bill.java
│   │   │   ├── Department.java
│   │   │   ├── Doctor.java
│   │   │   ├── LabTest.java
│   │   │   ├── Patient.java
│   │   │   ├── Person.java
│   │   │   ├── Room.java
│   │   │   └── TreatmentRecord.java
│   │   │
│   │   ├── repository/
│   │   │   ├── AdmissionRepository.java
│   │   │   ├── AppointmentRepository.java
│   │   │   ├── BillRepository.java
│   │   │   ├── DepartmentRepository.java
│   │   │   ├── DoctorRepository.java
│   │   │   ├── LabTestRepository.java
│   │   │   ├── PatientRepository.java
│   │   │   ├── RoomRepository.java
│   │   │   └── TreatmentRecordRepository.java
│   │   │
│   │   ├── service/
│   │   │   ├── admission/
│   │   │   ├── appointment/
│   │   │   ├── billing/
│   │   │   ├── department/
│   │   │   ├── doctor/
│   │   │   ├── laboratory/
│   │   │   ├── patient/
│   │   │   ├── payment/
│   │   │   ├── room/
│   │   │   └── treatment/
│   │   │
│   │   └── api/exception/
│   │       ├── ApiError.java
│   │       ├── BusinessRuleException.java
│   │       ├── DuplicateResourceException.java
│   │       ├── GlobalExceptionHandler.java
│   │       ├── InvalidAppointmentStatusException.java
│   │       ├── ResourceNotFoundException.java
│   │       └── RoomNotAvailableException.java
│   │
│   └── resources/
│       └── application.properties
│
├── sql/
│   ├── Dump file.sql
│   ├── smartcare_hostpital_system.sql
│   └── Task 06 – Task 10 SQL scripts
│
├── src/test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## 🛠️ Technologies Used

| Technology          | Purpose                       |
| ------------------- | ----------------------------- |
| ☕ Java 17           | Core programming language     |
| 🌱 Spring Boot      | Backend application framework |
| 🌐 Spring Web       | REST API development          |
| 🗃️ Spring Data JPA | Database access               |
| 🐘 Hibernate        | ORM                           |
| 🐬 MySQL            | Relational database           |
| 📦 Maven            | Dependency management         |
| 🧩 Lombok           | Reducing boilerplate code     |
| ✅ Spring Validation | Request validation            |
| 🧪 JUnit            | Testing                       |
| 🎭 Mockito          | Mock-based testing            |
| 🗄️ H2              | Test database                 |
| 🔧 Git              | Version control               |
| 📮 Postman          | API testing                   |

---

## 🧠 Object-Oriented Programming

The project demonstrates the four major OOP principles.

### Encapsulation

Entity fields are encapsulated using private fields and accessor methods.

```java
private String fullName;
private LocalDate dob;
private String contactNumber;
```

Getters and setters provide controlled access to the data.

### Inheritance

`Patient` inherits from the abstract `Person` class.

```java
public class Patient extends Person {
    // Patient-specific properties
}
```

This allows common properties such as name, date of birth, gender, address, and contact information to be reused.

### Abstraction

The project uses interfaces such as:

```java
public interface PaymentService {
    PaymentMethod getPaymentMethod();

    boolean processPayment(
        String billId,
        BigDecimal amount
    );
}
```

The interface defines the required payment behavior without exposing implementation details.

### Polymorphism

Different payment services implement the same `PaymentService` interface:

```text
PaymentService
      │
      ├── CardPaymentService
      ├── CashPaymentService
      └── OnlinePaymentService
```

Each implementation can provide its own payment-processing behavior.

---

## ⚠️ Exception Handling

SmartCare uses centralized exception handling with Spring's:

```java
@RestControllerAdvice
```

The project contains several custom exceptions.

### ResourceNotFoundException

Used when a requested resource cannot be found.

```java
throw new ResourceNotFoundException(
    "Patient not found: " + patientId
);
```

Returns:

```text
HTTP 404 NOT FOUND
```

### DuplicateResourceException

Used when attempting to create an already-existing resource.

```java
throw new DuplicateResourceException(
    "Admission already exists"
);
```

Returns:

```text
HTTP 409 CONFLICT
```

### BusinessRuleException

Used when a business rule is violated.

### RoomNotAvailableException

Used when an unavailable room is requested.

### InvalidAppointmentStatusException

Used when an invalid appointment status is provided.

---

## 🔗 REST API Endpoints

### Patient API

Base URL:

```text
/api/patients
```

| Method | Endpoint             | Description       |
| ------ | -------------------- | ----------------- |
| GET    | `/api/patients`      | Get all patients  |
| GET    | `/api/patients/{id}` | Get patient by ID |
| POST   | `/api/patients`      | Create patient    |
| PUT    | `/api/patients/{id}` | Update patient    |
| DELETE | `/api/patients/{id}` | Delete patient    |

---

### Doctor API

Base URL:

```text
/api/doctors
```

| Method | Endpoint                                            | Description              |
| ------ | --------------------------------------------------- | ------------------------ |
| GET    | `/api/doctors`                                      | Get all doctors          |
| GET    | `/api/doctors/{id}`                                 | Get doctor by ID         |
| GET    | `/api/doctors/specialization/{specialization}`      | Search by specialization |
| POST   | `/api/doctors`                                      | Create doctor            |
| PUT    | `/api/doctors/{id}`                                 | Update doctor            |
| PUT    | `/api/doctors/{doctorId}/department/{departmentId}` | Assign department        |
| DELETE | `/api/doctors/{id}`                                 | Delete doctor            |

---

### Appointment API

Base URL:

```text
/api/appointments
```

| Method | Endpoint                                | Description              |
| ------ | --------------------------------------- | ------------------------ |
| POST   | `/api/appointments`                     | Create appointment       |
| GET    | `/api/appointments`                     | Get all appointments     |
| GET    | `/api/appointments/{id}`                | Get appointment          |
| GET    | `/api/appointments/patient/{patientId}` | Get patient appointments |
| GET    | `/api/appointments/doctor/{doctorId}`   | Get doctor appointments  |
| PUT    | `/api/appointments/{id}`                | Update appointment       |
| DELETE | `/api/appointments/{id}`                | Delete appointment       |

---

### Admission API

Base URL:

```text
/api/admissions
```

| Method | Endpoint                              | Description            |
| ------ | ------------------------------------- | ---------------------- |
| POST   | `/api/admissions`                     | Create admission       |
| GET    | `/api/admissions`                     | Get all admissions     |
| GET    | `/api/admissions/{id}`                | Get admission          |
| GET    | `/api/admissions/patient/{patientId}` | Get patient admissions |
| PUT    | `/api/admissions/{id}`                | Update admission       |
| DELETE | `/api/admissions/{id}`                | Delete admission       |

---

### Room API

Base URL:

```text
/api/rooms
```

| Method | Endpoint                                 | Description            |
| ------ | ---------------------------------------- | ---------------------- |
| POST   | `/api/rooms`                             | Create room            |
| GET    | `/api/rooms`                             | Get all rooms          |
| GET    | `/api/rooms/{id}`                        | Get room               |
| GET    | `/api/rooms/category/{category}`         | Search by category     |
| GET    | `/api/rooms/availability/{availability}` | Search by availability |
| PUT    | `/api/rooms/{id}`                        | Update room            |
| DELETE | `/api/rooms/{id}`                        | Delete room            |

---

### Laboratory API

Base URL:

```text
/api/lab-tests
```

| Method | Endpoint                             | Description            |
| ------ | ------------------------------------ | ---------------------- |
| POST   | `/api/lab-tests`                     | Create laboratory test |
| GET    | `/api/lab-tests`                     | Get all tests          |
| GET    | `/api/lab-tests/{labTestId}`         | Get test               |
| GET    | `/api/lab-tests/patient/{patientId}` | Get patient tests      |
| GET    | `/api/lab-tests/doctor/{doctorId}`   | Get doctor tests       |
| PUT    | `/api/lab-tests/{labTestId}`         | Update test            |
| DELETE | `/api/lab-tests/{labTestId}`         | Delete test            |

---

### Billing API

Base URL:

```text
/api/billing
```

| Method | Endpoint                           | Description       |
| ------ | ---------------------------------- | ----------------- |
| POST   | `/api/billing`                     | Create bill       |
| GET    | `/api/billing`                     | Get all bills     |
| GET    | `/api/billing/{billId}`            | Get bill          |
| GET    | `/api/billing/patient/{patientId}` | Get patient bills |
| PUT    | `/api/billing/{billId}`            | Update bill       |
| DELETE | `/api/billing/{billId}`            | Delete bill       |

---

### Department API

Base URL:

```text
/api/departments
```

| Method | Endpoint                | Description         |
| ------ | ----------------------- | ------------------- |
| POST   | `/api/departments`      | Create department   |
| GET    | `/api/departments`      | Get all departments |
| GET    | `/api/departments/{id}` | Get department      |
| PUT    | `/api/departments/{id}` | Update department   |
| DELETE | `/api/departments/{id}` | Delete department   |

---

### Treatment Record API

Base URL:

```text
/api/treatment-records
```

| Method | Endpoint                                     | Description             |
| ------ | -------------------------------------------- | ----------------------- |
| POST   | `/api/treatment-records`                     | Create treatment record |
| GET    | `/api/treatment-records`                     | Get all records         |
| GET    | `/api/treatment-records/{id}`                | Get record              |
| GET    | `/api/treatment-records/patient/{patientId}` | Get patient records     |
| GET    | `/api/treatment-records/doctor/{doctorId}`   | Get doctor records      |
| PUT    | `/api/treatment-records/{id}`                | Update record           |
| DELETE | `/api/treatment-records/{id}`                | Delete record           |

---

## 🗄️ Database

The project uses **MySQL** as its primary relational database.

Database:

```text
smartcare_db
```

The project includes SQL scripts for:

* Database creation
* Table creation
* Sample data
* SQL queries
* Database tasks
* Testing queries

The main SQL files are located inside:

```text
sql/
```

---

## 🔄 Application Flow

A typical request follows this flow:

```text
Client
   │
   │ HTTP Request
   ▼
Controller
   │
   ▼
DTO
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
MySQL Database
   │
   ▼
Repository
   │
   ▼
Service
   │
   ▼
Response DTO
   │
   ▼
Controller
   │
   ▼
HTTP Response
```

---

## 💳 Payment Architecture

The payment module demonstrates interface-based abstraction and polymorphism.

```text
                    PaymentService
                          │
             ┌────────────┼────────────┐
             ▼            ▼            ▼
       CardPayment   CashPayment   OnlinePayment
         Service        Service       Service
```

A `PaymentFactory` is used to obtain the appropriate payment service based on the selected payment method.

This makes the payment module easier to extend with additional payment methods.

---

## 🧪 Testing

The project includes testing using:

* JUnit
* Mockito
* Spring Boot Test
* H2 test database

Example test location:

```text
src/test/java/
```

The test environment can use H2 instead of the production MySQL database.

---

## 🚀 Getting Started

### Prerequisites

Make sure you have installed:

* Java 17 or later
* Maven
* MySQL
* Git
* Postman (recommended for API testing)

---

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/smartcare-hospital-management-system.git
```

```bash
cd smartcare-hospital-management-system
```

---

### 2. Create the MySQL database

Open MySQL and create:

```sql
CREATE DATABASE smartcare_db;
```

You can also use the SQL scripts included in:

```text
sql/
```

to create the required tables and sample data.

---

### 3. Configure the database

Update:

```text
src/main/resources/application.properties
```

Use your own local database credentials.

For example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartcare_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update

server.port=8080
```

> ⚠️ **Security:** Never commit your real database password, API keys, tokens, or other credentials to GitHub.

---

### 4. Build the project

Using Maven:

```bash
mvn clean install
```

Or using the Maven wrapper:

```bash
./mvnw clean install
```

On Windows:

```cmd
mvnw.cmd clean install
```

---

### 5. Run the application

```bash
mvn spring-boot:run
```

Or:

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## 📮 API Testing

The REST APIs can be tested using **Postman**.

Example:

```http
GET http://localhost:8080/api/patients
```

Example POST request:

```http
POST http://localhost:8080/api/patients
Content-Type: application/json
```

The request body depends on the corresponding DTO.

---

## 📚 Key Learning Outcomes

Through this project, I gained practical experience with:

* Building RESTful APIs using Spring Boot
* Designing layered backend applications
* Implementing CRUD operations
* Working with MySQL and JPA
* Creating entity relationships
* Using DTOs for API requests and responses
* Applying OOP principles
* Implementing custom exceptions
* Handling exceptions globally
* Validating API requests
* Implementing business rules
* Using interfaces and polymorphism
* Writing unit tests with JUnit and Mockito
* Managing projects with Maven and Git

---

## 🔮 Future Improvements

Possible future improvements include:

* 🔐 User authentication and authorization
* 👥 Role-based access control
* 📊 Hospital management dashboard
* 📈 Reports and analytics
* 🔔 Notifications and reminders
* 📧 Email notifications
* 🧾 PDF invoice generation
* 📱 Mobile application
* ☁️ Cloud deployment
* 🐳 Docker containerization
* 📖 Swagger/OpenAPI documentation

---

## 👩‍💻 Author

**Gaveshika**

Student | Aspiring Data & Technology Professional

This project was developed as an academic project to gain practical experience in backend development, database management, object-oriented programming, and REST API design.

---

## 📄 License

This project is intended primarily for **educational and academic purposes**.

If you wish to reuse or modify this project, please provide appropriate attribution.
