# College Student Management Application

## Project Description
This Java-based College Student Management Application is designed to facilitate efficient management of student, staff, and administrative tasks in an educational environment. The application enables student attendance tracking, assignment management, timetable viewing, and role-based access for students, staff, and administrators.

## Features
- Student Dashboard: Students can view their attendance, assignments, and timetable.
- Staff Dashboard: Staff members can manage student attendance, assign assignments.
- Admin Dashboard: Administrators can manage student and staff data, as well as generate attendance and performance reports.
- Role-Based Access: Each user has access only to the features relevant to their role.

## Tech Stack
- Language: Java
- GUI: Swing for user interface
- Database: SQLite for data storage and management
- IDE: IntelliJ IDEA (recommended for ease of navigation and debugging)

## Getting Started
1. Download the project files.
2. Open the project in IntelliJ IDEA (or another preferred Java IDE).
3. Ensure SQLite is installed, and the database file (`college_management.db`) is in the correct directory.
4. Run the application from the main class, 'LoginFrame'.

## Directory Structure
src
└── com.college.management
    ├── dashboard
    │   ├── AdminDashboard         # Admin-specific dashboard features
    │   ├── Dashboard              # Base class for dashboards
    │   ├── DashboardFrame         # Frame to initialize dashboards based on user role
    │   ├── StaffDashboard         # Staff-specific dashboard features
    │   └── StudentDashboard       # Student-specific dashboard features
    ├── database
    │   └── DatabaseConnection     # Handles SQLite database connection
    ├── login
    │   ├── LoginFrame             # Handles login interface and functionality
    │   └── UserLoginInfo          # Data model for user login information
    ├── service
    │   └── UserService            # Business logic for user-related operations
    ├── ui
    │   ├── RegisterFrame          # Registration interface for new users
    │   └── TimetableDisplay       # UI for displaying the timetable
    └── util
        ├── ButtonEditor           # Custom button editor for tables
        └── ButtonRenderer         # Custom button renderer for tables

## Inference and Future Extension
This project demonstrates the application of Java, Swing, and SQLite in creating a role-based management system for educational institutions. It provides a foundation for further extensions, such as:
- Adding support for more advanced reporting and data analytics.
- Integrating online features, such as notifications and real-time data updates.
- Enabling customization options for students and staff, such as profile management.
- Adding a mobile application or web front-end to extend usability.
