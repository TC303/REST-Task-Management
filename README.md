# Task Management API

A comprehensive RESTful API for task management built with Spring Boot. This project demonstrates backend development best practices including clean architecture, security, testing, and API documentation.

## Features

- **User Management**: Create, read, update, and delete users with role-based access
- **Task Management**: Full CRUD operations for tasks with status tracking
- **Categories & Tags**: Organize tasks with categories and tags
- **Advanced Filtering**: Filter tasks by status, priority, category, due date, and tags
- **Security**: BCrypt password hashing and Spring Security integration
- **API Documentation**: Interactive Swagger UI for API exploration
- **Database Support**: H2 for development, PostgreSQL for production
- **Exception Handling**: Global exception handler with meaningful error messages
- **Validation**: Input validation using Bean Validation
- **Testing**: Unit and integration tests with JUnit 5 and Mockito

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database** (development)
- **PostgreSQL** (production)
- **Maven**
- **Lombok**
- **JUnit 5 & Mockito**
- **SpringDoc OpenAPI (Swagger)**

## Project Structure

```
src/
├── main/
│   ├── java/com/portfolio/taskmanagement/
│   │   ├── config/           # Configuration classes
│   │   ├── controller/       # REST controllers
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── exception/        # Custom exceptions and handlers
│   │   ├── model/            # JPA entities
│   │   ├── repository/       # JPA repositories
│   │   ├── service/          # Business logic
│   │   └── TaskManagementApplication.java
│   └── resources/
│       ├── application.yml           # Main configuration
│       ├── application-prod.yml      # Production configuration
│       └── db/migration/             # SQL migration scripts
└── test/                     # Unit and integration tests
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL (for production)

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd JavaProject
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Configuration

#### Development (H2 Database)
The application uses H2 in-memory database by default. Access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:taskdb`
- Username: `sa`
- Password: (leave empty)

#### Production (PostgreSQL)
Set the following environment variables:
```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/taskdb
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

Run with production profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## API Documentation

Interactive API documentation is available via Swagger UI:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

## API Endpoints

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/username/{username}` - Get user by username
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Tasks
- `GET /api/tasks?userId={userId}` - Get all tasks for a user
- `GET /api/tasks/{id}?userId={userId}` - Get task by ID
- `POST /api/tasks?userId={userId}` - Create new task
- `PUT /api/tasks/{id}?userId={userId}` - Update task
- `DELETE /api/tasks/{id}?userId={userId}` - Delete task
- `GET /api/tasks/status/{status}?userId={userId}` - Get tasks by status
- `GET /api/tasks/priority/{priority}?userId={userId}` - Get tasks by priority
- `GET /api/tasks/category/{categoryId}?userId={userId}` - Get tasks by category
- `GET /api/tasks/due-between?userId={userId}&start={start}&end={end}` - Get tasks due between dates

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create new category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Tags
- `GET /api/tags` - Get all tags
- `GET /api/tags/{id}` - Get tag by ID
- `POST /api/tags` - Create new tag
- `PUT /api/tags/{id}` - Update tag
- `DELETE /api/tags/{id}` - Delete tag

## Sample Data

The application includes sample data for testing:

### Users
- **Admin**: username: `admin`, password: `password123`
- **John Doe**: username: `john.doe`, password: `password123`
- **Jane Smith**: username: `jane.smith`, password: `password123`

### Categories
- Work, Personal, Shopping, Health

### Tags
- urgent, important, meeting, review, bug, feature, documentation

## Testing

Run tests with Maven:
```bash
mvn test
```

The project includes:
- **Unit Tests**: Service layer tests with Mockito
- **Integration Tests**: Controller tests with MockMvc

## Example Usage

### Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "password123",
    "firstName": "New",
    "lastName": "User"
  }'
```

### Create a Task
```bash
curl -X POST "http://localhost:8080/api/tasks?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Complete project",
    "description": "Finish the portfolio project",
    "status": "TODO",
    "priority": "HIGH",
    "categoryId": 1,
    "tagIds": [1, 2]
  }'
```

### Get Tasks by Status
```bash
curl -X GET "http://localhost:8080/api/tasks/status/TODO?userId=1"
```

## Future Enhancements

- JWT-based authentication and authorization
- Email notifications for task deadlines
- Task assignment and collaboration features
- File attachments for tasks
- Task comments and activity history
- Search functionality with Elasticsearch
- Caching with Redis
- Rate limiting
- Pagination and sorting
- WebSocket support for real-time updates

## Contributing

This is a portfolio project. Feel free to fork and customize for your own use.

## License

MIT License - feel free to use this project for learning and portfolio purposes.

## Contact

For questions or feedback, please reach out via GitHub issues.
