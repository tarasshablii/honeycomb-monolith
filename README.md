# Honeycomb Monolith

#### Hexagonal Modular Monolith Pattern for Agile Microservices Evolution

## Overview

Honeycomb Monolith is designed with a hexagonal modular structure that isolates domains and data,
ideal for seamless evolution into microservices. This architecture allows for flexible, maintainable, and
scalable software development.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

* JDK 17 (Amazon Corretto 17 recommended)
* Docker and Docker Compose

### Build

```shell
./gradlew clean build
```

### Run application

You can run the application either as Dockerized containers (recommended) or as standalone Spring Boot
services.

#### Run as docker containers

```shell
docker compose --profile monolith --profile microservices up -d
```

#### Run as Spring Boot services

1. Start the required dependencies `docker compose up -d`
2. Run monolith Spring Boot application `./gradlew :monolith:bootRun`
3. Run apigateway service `./gradlew :microservices:apigateway:bootRun`
4. Run initiatives service `./gradlew :microservices:initiatives:bootRun`
5. Run media service `./gradlew :microservices:media:bootRun`
6. Run sponsors service `./gradlew :microservices:sponsors:bootRun`

### Accessing the Application

* Feel free to use the attached [postman collection](postman-collection/Opora-API.postman_collection.json) to send
  requests to a running application.
* The monolith application will be accessible on http://localhost:8080.
* Monolith Swagger UI for API documentation can be accessed at http://localhost:8080/swagger-ui/index.html.
* The microservices application will be accessible on http://localhost:8090.
* Microservices Swagger UI for API documentation can be accessed at http://localhost:8090/swagger-ui/index.html.

### Stop application

* To stop monolith and microservices applications running in a containers,
  use `docker compose --profile monolith --profile microservices down -v`
* To stop dependencies, use `docker compose down -v`
* To stop Spring Boot applications running in shell, kill each job via `control+c` or `Ctrl+c`

# Reference Architecture

## Example implementation

This project provides a reference architecture through implementation of the
[Opora API](https://tarasshablii.github.io/opora-api/). The application enables managing humanitarian Initiatives to
address urgent crises and provide aid to those in need. Initiatives are managed by Sponsors - either individuals or
organizations. Application also provides means to store and retrieve Media. All inbound requests come through the
uniform API provided by API Gateway domainless module. For the sake of technological diversity Initiatives are stored in
MongoDB, Media files are stored in MinIO with metadata in PostgreSQL, and Sponsors reside in a separate instance of
PostgreSQL.
![Hexagonal Modular Diagram](images/honeycomb-monolith.png)
