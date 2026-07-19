# College Student Management System

A Java Swing desktop application for managing students, staff, and administrative tasks in an educational institution. Supports role-based dashboards, attendance tracking, assignment management, and timetable viewing, backed by a SQLite database.

## Features

- **Student Dashboard** — view attendance, assignments, and timetable
- **Staff Dashboard** — manage student attendance and assign assignments
- **Admin Dashboard** — manage student and staff data, generate attendance and performance reports
- **Role-Based Access** — each user sees only the features relevant to their role
- **Registration** — new users can register through a dedicated interface

## Tech Stack

| Component | Technology |
|---|---|
| Language | Java |
| GUI | Swing |
| Database | SQLite |
| IDE | IntelliJ IDEA (recommended) |

## Getting Started

### Prerequisites
- JDK 8 or higher
- IntelliJ IDEA (or any Java IDE)
- SQLite JDBC driver (already configured as a project library)

### Setup
1. Clone the repository
   ```bash
   git clone https://github.com/<your-username>/student-management-system.git
   ```
2. Open the project in IntelliJ IDEA
3. Make sure `college_management.db` is in the project root directory
4. Run the application from the main class: `LoginFrame`

## Project Structure

```
src
└── com.college.management
    ├── dashboard
    │   ├── AdminDashboard       # Admin-specific dashboard features
    │   ├── Dashboard            # Base class for dashboards
    │   ├── DashboardFrame       # Initializes dashboards based on user role
    │   ├── StaffDashboard       # Staff-specific dashboard features
    │   └── StudentDashboard     # Student-specific dashboard features
    ├── database
    │   └── DatabaseConnection   # Handles SQLite database connection
    ├── login
    │   ├── LoginFrame           # Login interface and logic
    │   └── UserLoginInfo        # Data model for user login info
    ├── service
    │   └── UserService          # Business logic for user-related operations
    ├── ui
    │   ├── RegisterFrame        # Registration interface for new users
    │   └── TimetableDisplay     # UI for displaying the timetable
    └── util
        ├── ButtonEditor         # Custom button editor for tables
        └── ButtonRenderer       # Custom button renderer for tables
```

## Roadmap

- [ ] Advanced reporting and data analytics
- [ ] Real-time notifications and data updates
- [ ] Profile management and customization for students/staff
- [ ] Web or mobile front-end

## License

This project is available for educational and personal use.
