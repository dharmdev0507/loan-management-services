# loan-management-services
loan management workflow using camunda

Loan Management System
Technology Stack
Java 17
Spring Boot 3.5.5
Camunda Platform 7.24.0
H2 Database
Spring Data JPA
Maven
JUnit 5
Features
Customer registration and KYC status
Loan application submission
Eligibility assessment
Camunda credit manager approval
Loan disbursement
EMI calculation
Repayment posting and history
Loan closure
Persistent H2 storage
Prerequisites

Install:

JDK 17
Maven 3.9+
IntelliJ IDEA or Eclipse

Verify:

bash
java -version
mvn -version

Build
bash
mvn clean install

Run tests
bash
mvn test

Start application
bash
mvn spring-boot:run


Application URL:

http://localhost:8080

Camunda

Tasklist:

http://localhost:8080/camunda/app/tasklist/

Cockpit:

http://localhost:8080/camunda/app/cockpit/

Local development credentials:

Username: admin
Password: admin123
H2 Database

Console:

http://localhost:8080/h2-console

JDBC URL:

text
jdbc:h2:file:./data/loan-management-db
Username: sa
Password: sa

REST API endpoints
Method	Endpoint
POST	/api/v1/customers
POST	/api/v1/loans
GET	/api/v1/loans/{id}
GET	/api/v1/loans/approvals/tasks
POST	/api/v1/loans/approvals/{taskId}/decision
POST	/api/v1/repayments
GET	/api/v1/repayments/{accountId}
POST	/api/v1/loans/{id}/close
