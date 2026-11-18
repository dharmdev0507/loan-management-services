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

==================
Customer onboarding

Register customer.

Capture name, email and phone.

Perform KYC verification.

Create customer record.

Database: CUSTOMER

Loan application

Select loan type.

Enter requested amount.

Select tenure.

Enter interest rate.

Generate unique application number.

Database: LOAN_APPLICATION

Eligibility assessment

Validate KYC.

Check loan amount.

Validate tenure.

Calculate eligibility.

Reject or proceed.

Camunda: Eligibility Service Task

Document verification

Collect income documents.

Verify identity documents.

Validate supporting documents.

Request missing documents if necessary.

Camunda: Document Verification User Task

Credit assessment and approval

Assess repayment capacity.

Calculate credit risk.

Assign credit manager.

Approve or reject loan.

Camunda: Credit Manager Approval

Loan agreement

Generate loan agreement.

Capture customer acceptance.

Store agreement and acceptance evidence.

Camunda: Agreement Acceptance

Loan disbursement

Validate approval.

Calculate EMI.

Create loan account.

Record disbursement transaction.

Generate repayment schedule.

Database: LOAN_ACCOUNT, EMI_SCHEDULE

Repayment management

Receive payment.

Validate payment reference.

Allocate interest and principal.

Update outstanding balance.

Update installment status.

Database: REPAYMENT, LOAN_TRANSACTION

Loan closure

Verify all dues.

Calculate final settlement.

Close account.

Generate closure certificate.

Notify customer.

Database: LOAN_ACCOUNT, LOAN_CLOSURE