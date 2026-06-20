# 📚 EduShare Resource Portal

A secure Java Swing-based desktop application designed to manage and distribute educational resources such as PDF notes, presentations, assignments, lab manuals, question papers, and study materials through a centralized MySQL database.

## 🎯 Project Overview

EduShare Resource Portal enables educational institutions to organize and share course-related files efficiently. Files are stored securely as BLOBs in a MySQL database and are categorized using course codes, allowing students and faculty to quickly access and download relevant materials.

The system follows a role-based approach:

* **Admin** can upload and manage educational resources.
* **Users** can only search, view, and download files.
* **No user-side upload or modification permissions** are provided, ensuring content integrity and security.

---

## ✨ Features

### Admin Module

* Upload files directly to the database.
* Store files as MySQL BLOBs.
* Associate files with course codes.
* Maintain metadata including:

  * Course Code
  * File Name
  * File Type
  * File Size
  * Upload Date & Time

### User Module

* Course code selection with search support.
* View available resources in tabular format.
* Download files to the local machine.
* Simple and intuitive Swing-based GUI.
* Read-only access to prevent unauthorized modifications.

---

## 🛡 Security Features

* Files stored securely as BLOBs in MySQL.
* Dedicated **read-only database user** for client applications.
* Separation of Admin and User privileges.
* SQL Injection protection using Prepared Statements.
* Restricted file upload access.
* Controlled database permissions.

---

## 🏗 System Architecture

```text
                 +----------------+
                 |     Admin      |
                 | Upload Utility |
                 +-------+--------+
                         |
                         v
                 +----------------+
                 | MySQL Database |
                 | Files as BLOBs |
                 +-------+--------+
                         |
                         v
                 +----------------+
                 |  Java Swing    |
                 | Client System  |
                 +-------+--------+
                         |
                         v
                 +----------------+
                 | File Download  |
                 +----------------+
```

---

## 💻 Technologies Used

* Java (JDK 11+)
* Java Swing
* MySQL Database
* JDBC (MySQL Connector/J)
* SQL

---

## 🗄 Database Schema

### Table: `files`

| Column      | Type              |
| ----------- | ----------------- |
| id          | INT (Primary Key) |
| course_code | VARCHAR           |
| file_name   | VARCHAR           |
| file_type   | VARCHAR           |
| file_size   | BIGINT            |
| uploaded_at | TIMESTAMP         |
| file_data   | LONGBLOB          |

---

## 🚀 How It Works

1. Admin uploads educational resources.
2. Files are stored in MySQL as BLOBs.
3. Users select a course code.
4. Available files are displayed in a table.
5. Users download selected files to their system.

---

## 📂 Project Structure

```text
EduShare/
│
├── src/
│   └── BUI.java
│
├── bin/
│   └── Compiled Classes
│
├── lib/
│   └── mysql-connector-j.jar
│
├── database/
│   └── SQL Scripts
│
└── README.md
```

---

## 🔮 Future Enhancements

* User Authentication System
* Role-Based Access Control (RBAC)
* Cloud Storage Integration
* File Preview Functionality
* Download Analytics
* Mobile Application Support
* Web-Based Version

---

## 📖 Learning Outcomes

This project demonstrates:

* Java Swing GUI Development
* JDBC Database Connectivity
* File Handling in Java
* BLOB Storage and Retrieval
* Database Security Practices
* Client-Server Application Design

---

## 👨‍💻 Author

**Aditya Aaryan**
B.Tech Computer Science Engineering
APJ Abdul Kalam Technological University

---

### Academic Resource Management Made Simple.

Secure • Organized • Accessible
