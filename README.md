# Mobile Provider Billing System API

A RESTful backend system for managing mobile usage, billing, and payments for subscribers. This project is developed as a midterm assignment for SE4458 using **Spring Boot**, **JWT Authentication**, and deployed on **Render.com**.

---

##  Project Links

- **GitHub Repository:** [https://github.com/Gizoskos/SE4458](https://github.com/Gizoskos/SE4458)
- **Swagger UI:** https://mobile-provider-api.onrender.com/swagger-ui/index.html
- **Project Presentation Video:** [GoogleDrive](https://drive.google.com/file/d/1_AxORA5zgcFXUMZpCBs5GKw8E_893pMN/view?usp=drive_link])

---

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Swagger (OpenAPI)
- Render.com for cloud deployment

---

## API Summary

| Endpoint                        | Auth | Paging | Description |
|---------------------------------|------|--------|-------------|
| `POST /api/v1/auth/register`   | ❌   | ❌     | Register a new user and auto-create subscriber |
| `POST /api/v1/auth/login`      | ❌   | ❌     | Login and receive JWT + subscriber ID |
| `POST /api/v1/usage`           | ✅   | ❌     | Add phone/internet usage |
| `POST /api/v1/billing/calculate`| ✅  | ❌     | Calculate bill for specific month |
| `GET  /api/v1/bill`            | ❌   | ❌     | View bill summary |
| `GET  /api/v1/bill-detailed`   | ✅   | ✅     | Detailed breakdown of the bill |
| `POST /api/v1/pay`             | ❌   | ❌     | Pay the bill (partial or full) |

---

## Billing Logic

- **Phone Calls:**
  - First **1000 minutes** are free
  - Each **extra 1000 minutes = $10**

- **Internet Usage:**
  - First **20GB (20480 MB) = $50**
  - Each **10GB (10240 MB) after that = $10**

---

## Authentication

- JWT is used for authorization
- Authenticated routes (`/usage`, `/bill-detailed`, `/calculate`) require a valid token in the `Authorization: Bearer <token>` header.
- Register/Login returns subscriber ID along with the token to identify users in Swagger.

---

## Assumptions & Design Decisions

-  A `Subscriber` is **automatically created** upon registration of an `AppUser`.
-  On login, the **subscriber ID is returned** along with the JWT token for convenience.
-  **Amount is not passed** to the calculate API; it is derived from usage records only.
-  Months are represented as strings like "May", "June", "September" instead of numeric formats. This is assumed throughout the system.
-  The billing system calculates the total amount only based on new usage that hasn’t been billed yet (billed = false).
-  Usage entities include a `billed` flag to ensure only **new usage records** are considered during calculation.
-  If the bill is fully paid (`isPaid = true`) and new usage is added, **a new bill is generated**.
-  A bill with isPaid = true cannot be recalculated. This prevents overwriting finalized bills.
-  If the bill has not been fully paid, the remaining unpaid amount will be added on top of the newly calculated charges.
-  Pay takes amount from the user for partial payments.
-  Swagger UI allows interaction with secure and public endpoints via JWT Bearer input.

---

## Data Model (ER Diagram)
![App Structure](https://github.com/user-attachments/assets/fb231699-f0ec-4ae2-803a-d633004127e2)

```text

AppUser: Login credentials

Subscriber: One per AppUser

Usage: Usage data for a subscriber in a month (billed flag added)

Bill: Stores calculated usage totals and payment info

```

##  Sample Scenarios

** Register → Login → Add Usage → Calculate Bill → Pay → Query Summary

** Add new usage to same month → Recalculate → If bill is paid → New bill created

** Add partial payment → Bill keeps remaining amount and is not marked as paid

** Swagger supports JWT token entry → Allows secure endpoint testing


##  How to Run Locally

```text
Clone the repo:

bash
Copy
Edit
git clone https://github.com/Gizoskos/SE4458.git
cd SE4458
Set up your database in src/main/resources/application.properties:

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://<HOST>:5432/<DB>
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>
Build and run the app:

bash
Copy
Edit
mvn clean install
mvn spring-boot:run
Swagger:
http://localhost:8080/swagger-ui/index.html
```

##  Issues Encountered & Fixes

Problem Solutions:
```text
Usage data was being double billed-->Added billed flag in Usage entity
Paid bills were being overwritten-->Prevented recalculation if isPaid=true
No subscriber reference on login-->Included subscriber ID in AuthResponse
Swagger JWT token not working-->Configured Swagger to accept Bearer tokens
```

##  Author
```text
Gizem Gültoprak
Software Engineering - Yaşar University
```
