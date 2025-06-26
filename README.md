# Billing Engine – Run & Test Instructions

## How to Run

### Run Locally (Using Maven)

1. Build the project:
   
   mvn clean install


2. Start the application:
   
   mvn spring-boot:run
   
   Or:
   java -jar target/billing-engine-0.0.1-SNAPSHOT.jar
   

3. Access the app:
   - Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`  
     - JDBC URL: `jdbc:h2:mem:billingdb`  
     - Username: `sa`  
     - Password: blank

-

### Run with Docker

1. Build Docker image:
      docker build -t billing-engine .

2. Run container:
   docker run -p 8080:8080 billing-engine
   

---

## How to Test

Use `curl`, Postman, or any REST client:


# Get premium schedule
curl http://localhost:8080/api/policies/POL123/schedule

# Record payment attempt
curl -X POST "http://localhost:8080/api/policies/POL123/payment?success=false"

# Get delinquent policies
curl http://localhost:8080/api/policies/delinquent

# Retry failed payments
curl -X POST http://localhost:8080/api/policies/POL123/retry

# Create policy with validation
curl -X POST http://localhost:8080/api/policies -H "Content-Type: application/json" -d '{"id":"POL123", "startDate":"2025-01-01", "endDate":"2025-04-01", "premiumAmount":150.0}'


Run automated tests:

mvn test

