
# Shopping List Manager

## Overview

This application is a Shopping List Manager designed to help users create, manage, and get recommendations for their shopping lists. It provides autocomplete suggestions, complementary item recommendations, and recipe suggestions as well.

## Features

- REST API with Swagger UI for easy testing and exploration.
- Integration with an in-memory H2 database.
- Basic HTTP authentication for API security.
- Docker support for easy deployment and running.
- Kubernetes integration for container orchestration and deployment.

## Future Work

- Implement user accounts and persistent storage with a production-grade database.
- Add role-based access control and more advanced authentication/authorization.
- Expand recipe and recommendation engine with machine learning or external APIs.
- Add UI frontend for better user interaction.
- Add tests for API endpoints and security features.

## Getting Started (Local Setup)

### Prerequisites

- Java JDK 17 or higher installed.
- Maven installed (or use your preferred build tool).
- Optional: Docker (for containerized setup).
- Git (for cloning the repo)

## How to Clone the Project from GitHub

To start working with this project locally, you first need to clone the repository from GitHub.

1. Make sure you have [Git](https://git-scm.com/) installed on your machine. You can verify this by running:

```bash
git --version
```

2. Clone the repository using the following command:

```bash
git clone https://github.com/nnc-spec/shopping-list-manager.git
```

3. Navigate to the project directory:

```bash
cd shopping-list-manager
```

### Running the Application Locally

1. Clone the repository.
2. Build the project with Maven:

   ```bash
   mvn clean install
   ```

3. Run the Spring Boot application:

   ```bash
   mvn spring-boot:run
   ```

4. Access the API documentation and test interface via Swagger UI:

   ```
   http://localhost:8080/swagger-ui.html
   ```

5. Access the H2 database console at:

   ```
   http://localhost:8080/h2-console
   ```

   Use the following credentials:

    - **JDBC URL**: `jdbc:h2:mem:testdb`
    - **Username**: `sa`
    - **Password**: (empty)

### Authentication

- The API is secured with HTTP Basic Authentication.
- Default credentials:
    - Username: `admin`
    - Password: `password`

- Swagger UI will prompt for these credentials when trying to access API endpoints.
- These information can be found and changed in application.yml file

## Running with Docker

### Prerequisites

- Docker installed and running on your machine.

### Build and Run

1. Build the Docker image:

   ```bash
   docker build -t shopping-list-manager .
   ```

2. Run the container:

   ```bash
   docker run -p 8080:8080 shopping-list-manager
   ```

3. Access Swagger UI and H2 console as described above using `http://localhost:8080`.

## Running with Minikube and Kubernetes

### Prerequisites

- Minikube installed and running.
- kubectl CLI installed.

### Steps

1. Configure Docker to use Minikube's Docker daemon:

   ```bash
   eval $(minikube docker-env)
   ```

2. Build the Docker image inside Minikube's Docker environment:

   ```bash
   docker build -t shopping-list-manager:latest .
   ```

3. Apply Kubernetes deployment and service YAML files (make sure these are in your repo):

   ```bash
   kubectl apply -f deployment.yaml
   kubectl apply -f service.yaml
   ```

4. Verify pods are running:

   ```bash
   kubectl get pods
   ```

5. Access the service (NodePort example):

   ```bash
   minikube service shopping-list-service
   ```
## Accessing Swagger UI in Kubernetes

When you deploy the application to Kubernetes using Minikube or any cluster, you might not have direct access to the application's ports from your local machine. To access the Swagger UI, you can use port forwarding.

#### You might get the following error so follow the steps in Port Forwarding section
Whitelabel Error Page
This application has no explicit mapping for /error, so you are seeing this as a fallback.

### Port Forwarding

Run the following command to forward the pod's port (default 8080) to your local machine:

```bash
kubectl port-forward deployment/shopping-list-deployment 8080:8080
```

### Access Swager UI from this url
```bash
http://127.0.0.1:8080/swagger-ui/index.html
```

### Notes

- Use `imagePullPolicy: Never` in your deployment.yaml to use the locally built image.
- Make sure your container port matches your application’s exposed port (usually 8080).

## Authentication Integration and Future Improvements

- The app uses Spring Security’s HTTP Basic Authentication for simplicity.
- Credentials are defined in the `SecurityConfig` class as in-memory users.
- For production, consider integrating with OAuth2 or JWT tokens.
- Store user credentials securely in a database or external identity provider.
- Add role-based access and permissions for finer control.
- Protect H2 console and other sensitive endpoints with stricter rules.

## Database Usage (Local and Docker)

- Uses in-memory H2 database by default (`jdbc:h2:mem:testdb`).
- Schema and data are auto-created on startup via Hibernate.
- You can access the H2 console at `/h2-console` endpoint.
- When running locally or inside Docker, the credentials are the same:
    - Username: `sa`
    - Password: (empty)
- For persistent or production use, replace with a persistent database (PostgreSQL, MySQL, etc.).

## Where to Find Credentials

- **API Authentication Credentials:** Set in `SecurityConfig.java`.
- **H2 Database Credentials:** Defined in `application.yml`:
    - Username: `sa`
    - Password: (empty)

## Summary

This project offers a solid starting point for a shopping list manager with basic recommendation and security features. The design allows easy extension to include more advanced authentication, persistent storage, and frontend UI. Running locally, with Docker, or with Minikube Kubernetes is straightforward, making it suitable for development and testing environments.

---

If you have any questions or need further assistance, feel free to ask!
