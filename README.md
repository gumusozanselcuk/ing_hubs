# Credit Module API

## Overview
This is a Spring Boot-based Credit Module API that allows users to manage laon-related operations. The application uses H2 as an in-memory database and includes authentication and authorization mechanisms.

## Features
- **User Authentication & Authorization**
- **Role-based Access Control** (ADMIN & CUSTOMER roles)
- **H2 Database Integration**
- **Secure API Endpoints**
- **Preloaded Test Data**

## Tech Stack
- **Spring Boot** (Spring Security, Spring Data JPA, Spring Web)
- **H2 Database** (In-memory)
- **JWT Authentication**
- **Postman** (For API Testing)

## Installation & Setup
### Prerequisites
- JDK 17+
- Maven

### Steps to Run the Project
1. Clone the repository:
   ```bash
   git clone git@github.com:gumusozanselcuk/ing_hubs.git
   ```
2. Build and run the application:
   Execute main method in the com/inghubs/creditmodule/CreditModuleApplication.java.Application from IDE.
3. The application will start on `http://localhost:8080`.

## Authentication & Authorization
- Obtain a JWT token by sending a POST request to:
  ```http
  POST /auth/login
  ```
    - Request Body:
      ```json
      {
        "username": "username",
        "password": "password"
      }
      ```
    - Response:
      ```json
        "jwt-token"
      ```
- Use the received token for authentication by adding it as a Bearer token in the `Authorization` header of each request.

## Role-Based Access Control
- **CUSTOMER Role**: Can only access endpoints if the `customer_id` in their JWT token matches the `customer_id` of the request params.
- **ADMIN Role**: Has unrestricted access to all endpoints.

### Predefined Users
Users are stored in-memory using Spring Security and have the following credentials:

| Username | Password | Role | Customer ID |
|----------|---------|------|-------------|
| `admin`  | `admin` | ADMIN | None        |
| `furkan` | `furkan` | CUSTOMER | 2           |

## API Endpoints

| Method | Endpoint                 | Description                                                                | Access |
|--------|--------------------------|----------------------------------------------------------------------------|--------|
| POST   | `/auth/login`            | Authenticate user and get JWT token.                                       | Public |
| GET    | `/api/v1/loans`          | Get all loans for customer. <br> RequestParam->customerId, page, size      | CUSTOMER (only if `customer_id` matches) / ADMIN |
| GET    | `/api/v1/loans/{loanId}` | Get all installments for loan. <br> PathVariable->loanId <br> RequestParam->customerId | CUSTOMER (only if `customer_id` matches) / ADMIN |
| POST   | `/api/v1/loans`          | Create loan. <br> RequestBody->LoanCreationRequestDTO                 | CUSTOMER (only if `customer_id` matches) / ADMIN |
| POST    | `/api/v1/loans/payment`  | Pay loan. <br> RequestBody->LoanPaymentRequestDTO                     | CUSTOMER (only if `customer_id` matches) / ADMIN |

## Loan Strategies
- There are 4 loan strategies for creating loan:<br> * TwentyFourMonthsLoanStrategy<br> * TwelveMonthsLoanStrategy<br> * NineMonthsLoanStrategy<br> * SixMonthsLoanStrategy
- Installment amounts and interest rate for loan are determined according to these strategies.
- The strategy is determined according to the parameter of the numberOfRequestedInstallments received from the user.

## Testing with Postman
A **Postman Collection** is provided to easily test all API endpoints. Follow these steps:
1. Import the provided Postman Collections under resources.
2. There are two collection. One of them is for CUSTOMER role user tests. One of them is ADMIN role user tests.
3. Firstly, authenticate using the `/auth/login` endpoint in both collections.
4. Copy the received JWT token.
5. Add the token as a Bearer Token in the Authorization header of all requests.
6. Ensure that CUSTOMER users only access their own data.
9. ADMIN users can test all endpoints.
10. There are also some test requests to see errors in both collections.

## Database
- The application uses an **H2 in-memory database**.
- The database is reset every time the application restarts.
- Dummy test data is loaded automatically from `import.sql`.
- You can access the H2 console at `http://localhost:8080/h2-console`.
- Default JDBC URL: `jdbc:h2:mem:credit_db`
- Username:admin<br>Password:admin

## License
This project is open-source and available under the MIT License.

