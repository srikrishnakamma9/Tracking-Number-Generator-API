
# Tracking Number Generator API

This is a scalable **Tracking Number Generator API** built with Spring Boot. The API generates unique tracking numbers based on customer IDs and timestamps, ensuring high availability and scalability with database and concurrency management.

## Features

- **Track Unique Numbers:** Generates unique tracking numbers by combining customer ID, timestamp, and a hash of query parameters.
- **Concurrency Management:** Uses database-based locking to ensure consistency and handle race conditions.
- **API Documentation:** Exposes a Swagger UI for easy exploration of the API.
- **Scalable Deployment:** Supports Dockerization for easy deployment to cloud platforms like AWS, GCP, and Heroku.
- **Testing Support:** Includes unit and integration tests.

## Requirements

- Java 17 (or later)
- Maven
- H2 Database (for local development)
- Docker (optional, for containerization)

## Setup and Installation

### Clone the repository

```bash
git clone https://github.com/yourusername/tracking-number-generator-api.git
cd tracking-number-generator-api
```

### Configure Application

#### `application.properties`
Make sure to configure your `application.properties` file with your desired database settings (e.g., H2, PostgreSQL, MySQL).

For **H2** database:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

For **PostgreSQL**, **MySQL**, or any other database, replace the H2 properties accordingly.

### Build and Run the Application

1. **Build with Maven:**

```bash
mvn clean install
```

2. **Run the Application:**

```bash
mvn spring-boot:run
```

3. **Access Swagger UI:**

Once the application starts, you can access the Swagger UI at:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

You can also access the API documentation in JSON format at:

[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Run with Docker (Optional)

To build and run the application in a Docker container:

1. **Create a Dockerfile** (already included in your project).
2. **Build the Docker image:**

```bash
docker build -t tracking-number-generator-api .
```

3. **Run the Docker container:**

```bash
docker run -p 8080:8080 tracking-number-generator-api
```

### Deployment

#### Deploy to Heroku

1. Login to Heroku:

```bash
heroku login
```

2. Initialize a Git repository (if not already done):

```bash
git init
```

3. Create a new Heroku app:

```bash
heroku create tracking-number-generator-api
```

4. Push the app to Heroku:

```bash
git push heroku master
```

5. Open the app in the browser:

```bash
heroku open
```

### Testing

The application includes unit and integration tests, which can be run using:

```bash
mvn test
```

## API Endpoints

### 1. Generate Tracking Number

- **URL:** `/api/generate-tracking-number`
- **Method:** `GET`
- **Request Parameters:**
  - `customerId` (required): The ID of the customer.
  - `timestamp` (required): The timestamp for the tracking number.
  - Other optional query parameters to include in the hash.

- **Response:**
  - `trackingNumber`: A unique tracking number.

### 2. Retrieve Tracking Number by ID

- **URL:** `/api/tracking-number/{trackingNumber}`
- **Method:** `GET`
- **Response:**
  - `trackingNumber`: The generated tracking number.
  - `createdAt`: The creation timestamp of the tracking number.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

### Notes:

- **Swagger UI**: Make sure your application is running and you can access the Swagger UI at `http://localhost:8080/swagger-ui/index.html`.
- **Heroku Deployment**: For a smoother Heroku deployment, you may need to configure your `Procfile` for Heroku-specific setup, especially for database connections.
