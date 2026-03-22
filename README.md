# Currency Converter API

A robust RESTful web service built with **Spring Boot** that allows users to perform currency conversions. The application features user management and a secure conversion engine protected by custom **API Key Authentication**.

## 🚀 Tech Stack
* **Java 21+**
* **Spring Boot 4.x** (Web, Security, Data JPA, Validation)
* **Database:** H2 (Configure in `application.yml`)
* **Testing:** JUnit 5, Mockito, Spring Boot Test (`@WebMvcTest`)
* **Build Tool:** Maven

## ✨ Features
* **User Management:** Full CRUD operations for managing system users.
* **Currency Conversion:** Calculates exchange rates between different currencies based on provided amounts.
* **Custom Security:** Endpoints are protected via a custom `ApiKeyFilter` utilizing the `X-API-KEY` HTTP header, ensuring only authorized applications/users can perform conversions.
* **Input Validation:** Strict validation using `jakarta.validation` (`@Valid`) to prevent bad requests (e.g., negative amounts, missing fields).
* **Comprehensive Testing:** Controller layer is fully covered by unit tests using `MockMvc` with simulated I/O and isolated security contexts.

## 🔒 Security (API Key)
The conversion endpoint is secured. To access it, you must pass your API key in the HTTP headers:
`X-API-KEY: your_api_key_here`

If the key is missing or invalid, the API will respond with `403 Forbidden` or `401 Unauthorized` handled by a custom `AuthenticationEntryPoint` and `AccessDeniedHandler`.

## 📡 API Reference

### User Controller (`/api/users`)
* `GET /api/users` - Retrieve a list of all users.
* `GET /api/users/{id}` - Retrieve a specific user by ID.
* `POST /api/users` - Create a new user.
  ```json
  {
    "username": "John Doe"
  }
  ```
* `PUT /api/users/{id}` - Update an existing user.
* `DELETE /api/users/{id}` - Delete a user.
### Conversion Controller (`/api/convert`)
* `POST /api/convert` - Perform a currency conversion. (Requires X-API-KEY header)
  ```json
  {
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "amount": 100.00
  }
  ```
* Response:
  ```json
  {
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "amount": 100.00,
  "rate": 0.85,
  "convertedAmount": 85.00
  }
  ```
## 🛠️ How to Run Locally
1. Clone the repository:
```bash
  git clone [https://github.com/Vlad110200/currency-converter.git](https://github.com/your-username/currency-converter.git)
```
2. Navigate to the directory:
```bash
  cd currency-converter
```
3. Configure the Database:
   Update the src/main/resources/application.yml file with your database credentials.
4. Build and Run:
```bash
  mvn clean install
  mvn spring-boot:run
```
5. Run the Tests:
```bash
  mvn test 
```