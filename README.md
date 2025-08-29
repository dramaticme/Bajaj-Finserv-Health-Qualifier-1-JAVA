#  ☃️Fast Webhook SQL Submission App – HealthRx 

**Welcome to your automated SQL submission Spring Boot app!**  
This repository contains a **ready-to-run Spring Boot application** that automates the HealthRx SQL assignment workflow, ensuring you submit the correct SQL query via webhook securely using JWT.

Whether you want to **practice automated API submissions** or **test SQL queries locally**, this project provides a hands-on example with a clean, modular setup.

---

## ♨️ Core Features Covered

### (^▽^) Automated Assignment Flow
- Generates a webhook automatically on startup.
- Picks the correct SQL query based on the registration number.
- Submits the SQL securely to the webhook using the provided JWT.

### (^^ゞ Optional Local SQL Testing
- Connect to a MySQL database (`students`) to validate SQL queries.
- Test queries safely using `DBConnection` and `TestDB.java`.

### (╯▽╰ ) Modular & Configurable
- Clean separation of **config, DTOs, runner, SQL, and DB logic**.
- Easily adaptable for other SQL/API workflows.

---

## ^^ Who This Is For 🌌

- **Backend Developers practicing automated API submissions**
- **Students learning Spring Boot + WebClient + JWT**
- **Anyone preparing for SQL + REST integration exercises**

---

## 🐞 Tech Stack

| Tool                  | Purpose                                      |
|-----------------------|----------------------------------------------|
| **Java 17**           | Core programming language                     |
| **Spring Boot 3.3.2** | Application framework for easy bootstrapping |
| **WebClient**          | Reactive HTTP requests                        |
| **MySQL**             | Optional local database for testing queries   |
| **Maven**             | Dependency management and build tool          |

---

## 🐲 Getting Started

1. Clone the repository:
  ```bash
git clone https://github.com/your-username/fast-webhook-sql.git
cd fast-webhook-sql
Update application.yml with your candidate info:

yaml
Copy code
candidate:
  name: John Doe
  regNo: REG12347
  email: john@example.com

healthrx:
  baseUrl: https://bfhldevapigw.healthrx.co.in/hiring
  generatePath: /generateWebhook/JAVA
  fallbackSubmitPath: /testWebhook/JAVA
  useBearerPrefix: true
Install dependencies and build the project:
```
```
bash
Copy code
mvn clean install -DskipTests
mvn package -DskipTests
Run the app (it will automatically execute the flow):
```
```
bash
Copy code
java -jar target/fast-webhook-sql-1.0.0.jar
🥤 Optional: Local SQL Testing
Create MySQL database:

sql
Copy code
CREATE DATABASE students;
USE students;

CREATE TABLE Students (
    name VARCHAR(50),
    regno VARCHAR(10),
    email VARCHAR(50)
);
Insert sample data:

sql
Copy code
INSERT INTO Students (name, regno, email) VALUES
('John Doe', 'REG12347', 'john@example.com');
Run TestDB.java to validate your SQL query.

```
---

## 🥤🍀 About the Author
Sakshi Bhojraj Sonkusare
Aspiring full-stack developer with a passion for building real-world software solutions and automating workflows.

LinkedIn: https://www.linkedin.com/in/sakshi-sonkusare-381362354/

Portfolio: Coming Soon

---
## Contributions and Support ❤️
Feedback, feature suggestions, and contributions are welcome. Consider starring the repo on GitHub or submitting a pull request if you improve the workflow.

