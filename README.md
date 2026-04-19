# NUST Attendance Tracker

> **SPS611S Software Processes — Group Project 2026**
> Digital Class/Lesson Attendance Tracking System for the Namibia University of Science and Technology (NUST).

---

##  Group Members

| Name | Student Number |
|------|---------------|
| Ndati Kafidi | 224066765 |
| Jedidja Mbinga | 224016148 |
| Bernard Fotolela | 224060533 |
| Petrus Amukugo | 224032119 |
| Esegel Narib | 223086770 |
| Twapewoshinge Shooya | 223023434 |

---

##  Project Overview

A web-based attendance tracking system to replace NUST's manual paper-based attendance process. The system allows lecturers to mark student attendance digitally, students to view their own records, and administrators to manage users and generate reports.

### Key Features
-  Role-based authentication (Student / Lecturer / Admin)
-  Digital attendance marking per session
-  Attendance reports with PDF/Excel export
-  Automatic low-attendance alerts (below 80%)
-  QR code-based student self check-in
-  Admin audit log

---

##  Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17 + Spring Boot 3 |
| Frontend | HTML5 / CSS3 / JavaScript (Vanilla) |
| Database | MySQL 8 |
| ORM | Spring Data JPA (Hibernate) |
| Auth | Spring Security + JWT |
| Build | Maven |
| Version Control | Git + GitHub |

---

##  Project Structure

```
nust-attendance-tracker/
├── src/
│   ├── main/
│   │   ├── java/com/nust/attendance/
│   │   │   ├── config/          # Spring Security, JWT config
│   │   │   ├── controller/      # REST API controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── model/           # JPA entity classes
│   │   │   ├── repository/      # Spring Data JPA repositories
│   │   │   ├── service/         # Business logic layer
│   │   │   ├── security/        # JWT filter, user details
│   │   │   └── util/            # Helper utilities
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/         # Stylesheets
│   │       │   ├── js/          # JavaScript files
│   │       │   └── images/      # Static assets
│   │       ├── templates/       # HTML templates (Thymeleaf)
│   │       └── application.properties
│   └── test/                    # Unit and integration tests
├── database/
│   ├── schema.sql               # Full DB schema
│   └── seed.sql                 # Sample/test data
├── docs/                        # Project deliverables
├── pom.xml                      # Maven build file
└── .gitignore
```

---

##  Setup & Running Locally

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Clone the repo
```bash
git clone https://github.com/YOUR_USERNAME/nust-attendance-tracker.git
cd nust-attendance-tracker
```

### 2. Create the database
```bash
mysql -u root -p
```
```sql
CREATE DATABASE nust_attendance;
EXIT;
```
Then run the schema:
```bash
mysql -u root -p nust_attendance < database/schema.sql
mysql -u root -p nust_attendance < database/seed.sql
```

### 3. Configure application properties
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nust_attendance
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
```

### 4. Run the application
```bash
mvn spring-boot:run
```
App runs at: `http://localhost:8080`

---

##  Running Tests
```bash
mvn test
```

---

##  Submission Schedule

| Deliverable | Due Date | Status |
|-------------|----------|--------|
| Project Proposal | 29 Mar 2026 |  Done |
| Process Model & Justification | 29 Mar 2026 |  Done |
| Requirements Specification (SRS) | 5 Apr 2026 |  Done |
| System Design | 5 Apr 2026 |  Done |
| Prototype / Wireframes | 5 Apr 2026 |  Done |
| Implementation | 19 Apr 2026 |  In Progress |
| Testing | 19 Apr 2026 |  Pending |
| Final Presentation & Demo | 26 Apr 2026 |  Pending |

---

##  Process Model
This project follows **Agile Scrum** with 4 sprints. See `docs/` for the full process artefacts.

---

*Namibia University of Science and Technology — Faculty of Computing and Informatics*
*Course: SPS611S Software Processes | 2026*
