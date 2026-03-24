# EventFlow

> Event-driven microservices platform for order management

**Author:** Fodoup Christian (fodoup@gmail.com)

---

## What is EventFlow?

EventFlow is a distributed, event-driven application built with Spring Boot and Apache Kafka. It handles order placement, stock management, and notifications through loosely coupled microservices that communicate asynchronously via Kafka events.

When an order is placed, an event is broadcast on a Kafka topic. Each downstream service reacts independently: the inventory service updates stock, and the notification service confirms the order — no direct service-to-service calls needed.

---

## Services

| Service               | Port | Role                                                |
|-----------------------|------|-----------------------------------------------------|
| `order-service`       | 8081 | Accepts orders via REST, publishes Kafka events     |
| `inventory-service`   | 8082 | Consumes order events, adjusts stock quantities     |
| `notification-service`| 8083 | Consumes order events, sends confirmations          |
| `user-service`        | —    | User management *(in progress)*                     |
| Keycloak              | 8080 | OAuth2 identity provider (realm: `eventflow`)       |
| Kafka                 | 9092 | Message broker                                      |
| Jenkins               | 9090 | CI/CD pipeline                                      |

---

## Event Flow

```
Client
  └── POST /orders
        └── Order Service
              ├── saves order to DB
              └── publishes → Kafka topic: "order-placed"
                                  ├── Inventory Service  → updates stock
                                  └── Notification Service → logs confirmation
```

**Kafka event format:** `{orderId}:{productId}:{quantity}`

---

## Tech Stack

| Layer           | Technology                              |
|-----------------|-----------------------------------------|
| Language        | Java 17                                 |
| Framework       | Spring Boot 4.x · Spring Security       |
| Messaging       | Apache Kafka · Zookeeper                |
| Authentication  | Keycloak 24 (OAuth2 / JWT)              |
| Database        | H2 (in-memory, per service)             |
| Build           | Maven 3.9                               |
| Containers      | Docker · Docker Compose                 |
| CI/CD           | Jenkins                                 |
| Utilities       | Lombok                                  |

---

## API Reference

All endpoints require a valid **Bearer JWT token** issued by Keycloak.

### Order Service — `http://localhost:8081`

| Method | Endpoint  | Description        |
|--------|-----------|--------------------|
| GET    | /orders   | List all orders    |
| POST   | /orders   | Place a new order  |

### Inventory Service — `http://localhost:8082`

| Method | Endpoint      | Description             |
|--------|---------------|-------------------------|
| GET    | /stock        | List all stock items    |
| GET    | /stock/{id}   | Get a stock item by ID  |

---

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 17+
- Maven 3.9+

### Start the full stack

```bash
docker-compose up -d
```

Starts: PostgreSQL, Keycloak, Zookeeper, Kafka, and all microservices.

### Run a service locally

```bash
cd order-service
mvn spring-boot:run
```

### Get a JWT token from Keycloak

```bash
curl -X POST http://localhost:8080/realms/eventflow/protocol/openid-connect/token \
  -d "client_id=<your-client-id>" \
  -d "username=<username>" \
  -d "password=<password>" \
  -d "grant_type=password"
```

Use the `access_token` value as `Authorization: Bearer <token>` in your requests.

---

## CI/CD Pipeline

The `Jenkinsfile` defines the following stages:

1. **Checkout** — clones the repository
2. **Tests** — runs Maven tests for `order-service` and `inventory-service`
3. **Build Docker** — builds Docker images for all services
4. **Deploy** — brings up the stack with Docker Compose

Jenkins UI: `http://localhost:9090`

---

## Project Structure

```
eventflow/
├── order-service/           # Order management microservice
├── inventory-service/       # Stock management microservice
├── notification-service/    # Notification microservice
├── user-service/            # User management (in progress)
├── docker-compose.yml       # Full infrastructure definition
└── Jenkinsfile              # Jenkins pipeline
```

---

## License

This project is for educational and demonstration purposes.
