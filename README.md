# 🎟️ Ticketing Portal Web App

This is a full-featured, event ticketing portal built with **Spring Boot**, **Liquibase**, and **Dockerized PostgreSQL**. It allows users to view events, purchase tickets, and manage bookings.

---

## 🚀 Features

- **Event Management**: Create, view, and manage events
- **Ticket Types**: Each event supports multiple ticket types (e.g. VIP, General Admission)
- **User Registration & Authentication**: Secure login and signup
- **Booking System**: Book tickets with seat availability tracking
- **Liquibase Migrations**: Automatic database setup and schema evolution
- **Dockerized Setup**: Consistent dev environment with Docker Compose

---

## 🗂️ Code Structure

```
ticketing-portal/
├── src/
│   ├── main/
│   │   ├── java/com/ticketingportal/
│   │   │   ├── controller/      # REST endpoints (EventController, BookingController)
│   │   │   ├── model/           # JPA entities (Event, TicketType, Booking, User)
│   │   │   ├── dto/             # DTOs (EventDTO, TicketTypeDTO, BookingRequest, etc.)
│   │   │   ├── repository/      # Spring Data JPA Repositories
│   │   │   └── security/        # JWT and Security Configuration
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/changelog/    # Liquibase changelogs
├── Dockerfile                   # Containerizes the Spring Boot app
├── docker-compose.yml           # Sets up app + PostgreSQL container
└── LICENSE                      # Non-commercial use only
```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/yourusername/ticketing-portal.git
cd ticketing-portal
```

### 2️⃣ Start the App + Database with Docker Compose
```bash
docker-compose up --build
```

✅ This will:
- Start PostgreSQL on port 5432
- Start the Spring Boot app on port 8080

---

## 🛠️ Database Setup

- The app uses **Liquibase** to automatically set up all database tables and relationships.
- Changelogs are located in:
  ```
  src/main/resources/db/changelog/
  ```
- To add new tables or modify existing ones, simply add a new Liquibase changelog XML and include it in `db.changelog-master.xml`.

---

## 🔒 License

This project is licensed for **non-commercial use only**.  
For details, see [LICENSE](LICENSE).

For commercial licensing inquiries, please contact:
```
Bhaskar Mondal
bhaskarmondal.senor@gmail.com
```

---

## 📢 Contributing

PRs welcome!  
Feel free to submit issues or suggestions to improve this project.

---

## 🚀 Enjoy!

Happy hacking! 🎉
