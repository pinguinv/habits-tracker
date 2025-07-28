# Habits Tracker

A full-stack application for tracking progress in building habits.
Developed during my first apprenticeship in February 2023.

## Overview

Habits Tracker allows users to create, track, and monitor their habits with
features like adding new habits and viewing progress charts.
This was a learning project to build experience with full-stack development,
not intended for commercial use. It taught me a lot about new JS frameworks,
Spring, and database integration.

> **Note**: The UI is quite basic and not polished, so expect a rough visual experience!

## Technology Stack

- Spring 2.7.8
- Java 8
- Vue 3.2.45

## Prerequisites

- Node.js 18.x or later (includes npm)
- Java Development Kit (JDK) 8 or later
- MySQL 8.x (set up using XAMPP or similar)

## Project Structure

- `/frontend`: Vue.js frontend code
- `/src/main`: Spring backend code (Java)
- `/src/main/resources`: Backend configuration files (e.g., `application.properties`)

## Backend (Spring)

The backend is built with Spring and runs from the project root directory.
Hibernate automatically creates the database schema and tables based
on the defined entities.

### Database Setup

1. Install and start MySQL using XAMPP or a similar tool.
2. Create a database (e.g., `habits_tracker`).
3. Update `src/main/resources/application.properties` with your database details:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/habits_tracker
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Ensure MySQL is running before starting the backend.

> **Note**: The backend will fail to start if the MySQL database is not configured properly.

### Development

Start the backend:

```sh
./gradlew bootRun
```

### Build

Build the backend:

```sh
./gradlew build
```

## Frontend (Vue.js)

The frontend is built with Vue.js and located in the `frontend` directory.
Run all commands from the `/frontend` subdirectory.

### Installation

```sh
npm install
```

### Development

Start the development server with hot-reload:

```sh
npm run dev
```

### Build

Build the frontend for production:

```sh
npm run build
```

## Testing

Due to time constraints, unit and integration tests were not implemented.

## Troubleshooting

- **Backend fails to start**: Ensure MySQL is running and `application.properties`
  is correctly configured with the right database URL, username, and password.
- **Frontend build errors**: Run `npm install` again or clear the npm cache (`npm cache clean --force`).
