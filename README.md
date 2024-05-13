# Library Management System

This project is a Library Management System that provides functionalities for managing books, patrons, and borrowing records. It includes RESTful APIs for user authentication, book management, patron management, and borrowing record management.

## Features

- **User Authentication**: Users can register and authenticate to access the system.
- **Book Management**: CRUD operations for managing books, including addition, update, retrieval, and deletion.
- **Patron Management**: CRUD operations for managing patrons, including addition, update, retrieval, and deletion.
- **Borrowing Record Management**: Functions for recording book borrowing and return.

## Getting Started

To run the project locally, follow these steps:

1. Clone the repository to your local machine:

```bash
git clone https://github.com/AbdelrhmanSror/Library-Management-System.git
```

2. Navigate to the project directory:

```bash
cd Library-Management-System
```

3. Build the project using Maven:

```bash
mvn clean package
```

4. Run the application:

```bash
java -jar target/Library-Management-System.jar
```

The application will start on port `5000` by default.

## Testing

The project includes unit tests for ensuring the correctness and integrity of the implemented functionalities. You can run the tests using the following Maven command:

```bash
mvn test
```

---

## API Documentation

The project utilizes Swagger for API documentation. Once the application is running, you can access the Swagger UI by navigating to the following URL in your web browser:

```
http://localhost:5000/swagger-ui/index.html#/
```

From the Swagger UI, you can explore and interact with the available endpoints, view request and response schemas, and test the APIs directly.

### Getting Started:

1. **User Registration**: To access the endpoints, you need to register with a username and password. Use the following endpoint to register:

    - Endpoint: `POST /api/v1/authentication/register`
    - Example Request Body:
      ```json
      {
        "username": "your_username",
        "password": "your_password"
      }
      ```
    - Example Response:
      ```json
      {
        "result": "successfully added"
      }
      ```

2. **Authentication**: After registration, authenticate to obtain a JWT token for accessing other endpoints. Use the following endpoint:

    - Endpoint: `POST /api/v1/authentication`
    - Example Request Body:
      ```json
      {
        "username": "your_username",
        "password": "your_password"
      }
      ```
    - Example Response:
      ```json
      {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiMSIsImlkIjoxLCJzdWIiOiIxIiwiaWF0IjoxNzE1NTQ3MjA2LCJleHAiOjE3MTU1NTQ0MDZ9.g7QgZct0ttSxqd-iJlZbjZK4VOAFKTwjiManM6gk-LatsUxj8zUrghetwEGiLxLv-f3ITuzuoBY6SWMTfsq6cA"
      }
      ```

3. **Authorization**: Copy the JWT token received and click on the "Authorize" button at the top-right corner of the Swagger UI. Paste the token in the "Value" field, then click "Authorize" to enable access to secured endpoints.

4. **Using Endpoints**: Once authorized, you can use the endpoints as needed. Please note that to create a borrowing record, you must first add books and patrons and obtain their IDs, which you'll provide to the borrowing endpoint.

---
