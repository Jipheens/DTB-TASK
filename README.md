
 Banking Microservices Platform

This project implements a simplified microservices-based banking platform using Spring Boot. It provides functionality for managing Customers,Accounts and Cards, with soft delete, audit trails, and sensitive data masking (PAN/CVV).


 Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- Lombok
- SpringDoc OpenAPI (Swagger)



 Microservices Overview

1.Customer Service.
Manages customer bio data.

- `POST /api/customers` – Create customer
- `GET /api/customers/{id}` – Get customer by ID
- `PUT /api/customers/{id}` – Update customer
- `DELETE /api/customers/{id}` – Soft delete customer
- `GET /api/customers/search` – Search by name + creation date



2.Manages customer accounts.

- Each account belongs to **one** customer.
- `POST /api/accounts`
- `GET /api/accounts/search` – Search by IBAN/BIC/Card Alias



3. Card Service.
Manages physical/virtual cards.

- Each account can have up to **two** cards, **one per type** (physical or virtual).
- `POST /api/cards`
- `GET /api/cards/search` – Filter by PAN, card alias, and type
- PAN and CVV are masked unless `showSensitive=true` is passed.


Sensitive Data Masking

Card PAN and CVV are masked in responses by default:
```json
{
  "pan": "**** **** **** 1234",
  "cvv": "***"
}


Pass `?showSensitive=true` to view full values (e.g., for admin or test tools).



Database

* Uses PostgreSQL
* Each service manages its own DB
* Audit columns and soft delete (`deletedFlag`) handled via shared base class


Testing

* Unit tests are written using **JUnit 5**
* To run tests:



## 📑 Swagger / OpenAPI

* Swagger UI is available at:

Customer Service Swagger: http://localhost:1905/swagger-ui/index.html

Account Service Swagger: http://localhost:4200/swagger-ui/index.html

Card Service Swagger: http://localhost:4300/swagger-ui/index.html


NB: TO HIT THE ENDPOINTS YOU NEED TO GET AUTHENTICATED. ON STARTING THE APPLICATION, CHECK FOR THE LINE:
(Using generated security password:)  AND COPY THE KEY GENERATED FOR USE AS THE PASSWORD AND FOR THE USERNAME USE: user


Running Locally

Each service is a separate Spring Boot app.

### Prerequisites

* Java 17+
* PostgreSQL
* Maven 3+

### Steps

1. Configure PostgreSQL DBs for each service
2. Set DB credentials in each service's `application.yml`
3. Build all services:

   
   mvn clean install
   ```
4. Run each service individually:



Each service contains:

* `models`, `controllers`, `services`, `repositories`
* `Utils.shared` for audit/soft delete base entities


##  Author

Built by a Jipheens.

