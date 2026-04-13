# 👥 Dynamic User Management System

A robust REST API for managing users with **three-tier role-based access control** (USER / MODERATOR / ADMIN), built with Spring Boot, Spring Security, JWT, and MySQL.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Security | Spring Security + JWT |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL 8 |
| Build Tool | Maven |

---

## 📁 Project Structure

```
src/main/java/com/suyash/usermanagement/
├── controller/
│   ├── AuthController.java       # Register & Login
│   ├── UserController.java       # Self-service profile endpoints
│   ├── AdminController.java      # Full CRUD — ADMIN only
│   └── ModeratorController.java  # Read-only access — MODERATOR+
├── service/
├── serviceimpl/
├── repository/
├── model/
│   ├── User.java
│   └── Role.java                 # Enum: USER, MODERATOR, ADMIN
├── dto/
├── security/                     # JWT filter, UserDetailsService
├── config/                       # Security configuration
└── exception/                    # Custom exceptions + global handler
```

---

## ⚙️ Setup & Run

### Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.6+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Suyash1608/user-management-backend.git
   cd user-management-backend
   ```

2. **Configure MySQL** — update `application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Server starts at: `http://localhost:8081`

---

## 🔐 Role-Based Access Control

| Role | Permissions |
|------|------------|
| `USER` | View & update own profile, change password |
| `MODERATOR` | Read-only access to all users, search, filter by department |
| `ADMIN` | Full CRUD — create, update, delete users, assign roles, toggle status |

All new registrations default to **USER** role. Roles are elevated by an ADMIN.

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/auth/register` | Public | Register new user (role: USER) |
| POST | `/api/auth/login` | Public | Login and receive JWT token |

### User (Self-service)
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/users/me` | USER+ | Get own profile |
| PUT | `/api/users/me` | USER+ | Update own profile |
| PUT | `/api/users/me/change-password` | USER+ | Change own password |

### Moderator
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/moderator/users` | MODERATOR+ | List all users |
| GET | `/api/moderator/users/{id}` | MODERATOR+ | Get user by ID |
| GET | `/api/moderator/users/search?keyword=` | MODERATOR+ | Search users |
| GET | `/api/moderator/users/department/{dept}` | MODERATOR+ | Filter by department |

### Admin
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/admin/users` | ADMIN | List all users |
| GET | `/api/admin/users/{id}` | ADMIN | Get user by ID |
| GET | `/api/admin/users/search?keyword=` | ADMIN | Search users |
| GET | `/api/admin/users/role/{role}` | ADMIN | Filter by role |
| GET | `/api/admin/users/department/{dept}` | ADMIN | Filter by department |
| GET | `/api/admin/users/active` | ADMIN | List active users |
| PUT | `/api/admin/users/{id}` | ADMIN | Update user details |
| PUT | `/api/admin/users/{id}/role?role=` | ADMIN | Assign role |
| PUT | `/api/admin/users/{id}/toggle-status` | ADMIN | Activate / deactivate |
| DELETE | `/api/admin/users/{id}` | ADMIN | Delete user |

---

## 📋 Sample Requests

### Register
```json
POST /api/auth/register
{
  "firstName": "Suyash",
  "lastName": "Gupta",
  "email": "suyash@example.com",
  "password": "pass1234",
  "phone": "9876543210",
  "department": "Engineering"
}
```

### Assign Moderator Role (Admin only)
```
PUT /api/admin/users/2/role?role=MODERATOR
Authorization: Bearer <admin_token>
```

### Search Users
```
GET /api/admin/users/search?keyword=engineering
Authorization: Bearer <admin_token>
```

---

## ✅ Key Features

- **3-tier RBAC** — USER, MODERATOR, ADMIN with granular endpoint-level control
- **Self-service profile** — users manage own data without admin intervention
- **Full-text search** — search across name, email, department
- **Account activation toggle** — soft disable without deleting records
- **Secure password change** — validates current password before updating
- **Audit timestamps** — `createdAt` and `updatedAt` tracked automatically
- **Centralized exception handling** — consistent error responses across all endpoints

---

## 📬 Postman Collection

Import `User-Management-API.postman_collection.json` into Postman. Login requests auto-save tokens as collection variables.

---

## 👤 Author

**Suyash Gupta** — Java Backend Developer  
[LinkedIn](https://linkedin.com/in/suyash-16d08m/) | [GitHub](https://github.com/Suyash1608)
