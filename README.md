# 🛒 E-Commerce Microservices

A full-stack, production-grade e-commerce backend built with **Spring Boot**, **Spring Cloud**, and a rich ecosystem of microservices patterns. This project demonstrates real-world distributed systems design including async messaging, service discovery, centralized configuration, distributed tracing, and security via Keycloak.

---

## 📐 Architecture Overview

The system is composed of independent domain microservices that communicate synchronously via **OpenFeign / REST** and asynchronously via **Apache Kafka**. All services register with **Eureka** for discovery and are routed through a central **API Gateway**.

```
                        ┌──────────────┐
                        │  API Gateway │  ← Single entry point
                        └──────┬───────┘
                               │
          ┌────────────────────┼────────────────────┐
          │                    │                    │
   ┌──────▼──────┐    ┌────────▼──────┐    ┌───────▼───────┐
   │  Customer   │    │    Product    │    │     Order     │
   │  Service   │    │    Service    │    │    Service    │
   └─────────────┘    └───────────────┘    └───────┬───────┘
                                                   │ Kafka
                                          ┌────────▼───────┐
                                          │    Payment     │
                                          │    Service     │
                                          └────────┬───────┘
                                                   │ Kafka
                                          ┌────────▼───────┐
                                          │  Notification  │
                                          │    Service     │
                                          └────────────────┘
```

---

## 🧩 Services

| Service | Description | Database |
|---|---|---|
| **config-server** | Centralized configuration management for all microservices | — |
| **discovery-server** | Eureka service registry for dynamic service discovery | — |
| **api-gateway** | Single entry point; routes all client requests to appropriate services | — |
| **customer-service** | Manages customer profiles and data | MongoDB |
| **product-service** | Handles product catalog and inventory | PostgreSQL |
| **order-service** | Processes orders and coordinates with product/payment services | PostgreSQL |
| **payment-service** | Handles payment processing; publishes events to Kafka | PostgreSQL |
| **notification-service** | Listens to Kafka topics and sends email notifications | MongoDB |

---

## 🛠️ Tech Stack

### Core Framework
- **Java 17+**
- **Spring Boot 3**
- **Spring Cloud** (Config, Gateway, Netflix Eureka, OpenFeign)

### Messaging
- **Apache Kafka** + **Zookeeper** — async inter-service communication

### Databases
- **PostgreSQL** — relational data (orders, products, payments)
- **MongoDB** — document data (customers, notifications)

### Security
- **Keycloak** — OAuth2 / OpenID Connect identity provider

### Observability
- **Zipkin** — distributed tracing
- **Spring Boot Actuator** + **Micrometer** — metrics and health checks

### Infrastructure
- **Docker** + **Docker Compose** — containerized local environment
- **MailDev** — local SMTP server for testing email notifications

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.8+
- Docker & Docker Compose

### 1. Clone the Repository

```bash
git clone https://github.com/ali-bouali/microservices-full-code.git
cd microservices-full-code
```

### 2. Start Infrastructure

Bring up all required infrastructure containers (PostgreSQL, MongoDB, Kafka, Zookeeper, Zipkin, Keycloak, MailDev):

```bash
docker compose up -d
```

This starts the following containers:

| Container | Port |
|---|---|
| PostgreSQL | `5432` |
| MongoDB | `27017` |
| Apache Kafka | `9092` |
| Zookeeper | `2181` |
| Zipkin | `9411` |
| Keycloak | `8181` |
| MailDev UI | `1080` |
| MailDev SMTP | `1025` |

### 3. Start the Services

Start each service in the following order (to respect dependencies):

```bash
# 1. Config Server
cd services/config-server && mvn spring-boot:run

# 2. Discovery Server (Eureka)
cd services/discovery-server && mvn spring-boot:run

# 3. Domain Microservices (can be started in any order after the above)
cd services/customer  && mvn spring-boot:run
cd services/product   && mvn spring-boot:run
cd services/order     && mvn spring-boot:run
cd services/payment   && mvn spring-boot:run
cd services/notification && mvn spring-boot:run

# 4. API Gateway (start last)
cd services/api-gateway && mvn spring-boot:run
```

---

## 🔄 Inter-Service Communication

### Synchronous (OpenFeign / REST)
- `order-service` → `customer-service` (validate customer)
- `order-service` → `product-service` (check product availability)

### Asynchronous (Kafka)
- `order-service` → publishes order confirmation event
- `payment-service` → consumes order event, publishes payment result
- `notification-service` → consumes payment event, sends email

---

## 🔍 Observability

Access Zipkin to visualize distributed traces across all services:

```
http://localhost:9411
```

All services are configured with `sampling.probability: 1.0` to trace every request.

---

## 🔐 Security (Keycloak)

The API Gateway is secured using Keycloak as an OAuth2 authorization server.

**Keycloak Admin Console:** `http://localhost:8181`
- Username: `admin`
- Password: `admin`

---

## 📬 Email Notifications (MailDev)

Outgoing emails (e.g., order confirmations, payment receipts) can be viewed in the MailDev inbox:

```
http://localhost:1080
```

---

## 📁 Project Structure

```
microservices-full-code/
└── services/
    ├── config-server/        # Centralized config
    ├── discovery-server/     # Eureka service registry
    ├── api-gateway/          # Spring Cloud Gateway
    ├── customer/             # Customer domain service
    ├── product/              # Product catalog service
    ├── order/                # Order processing service
    ├── payment/              # Payment processing service
    └── notification/         # Email notification service
```

---

## 📚 Key Concepts Demonstrated

- ✅ **Service Discovery** with Netflix Eureka
- ✅ **Centralized Configuration** with Spring Cloud Config Server
- ✅ **API Gateway** with Spring Cloud Gateway
- ✅ **Synchronous Communication** with OpenFeign
- ✅ **Asynchronous Messaging** with Apache Kafka
- ✅ **Distributed Tracing** with Zipkin + Micrometer
- ✅ **OAuth2 Security** with Keycloak
- ✅ **Containerized Infrastructure** with Docker Compose
- ✅ **Multi-Database Architecture** (PostgreSQL + MongoDB)

---

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

---

## 📄 License

This project is licensed under the MIT License.
