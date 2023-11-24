# Honeycomb Monolith

Hexagonal modular monolith structure for microservices evolution

## Overview

Honeycomb Monolith is designed with a hexagonal modular structure that isolates domains and data,
ideal for evolving into microservices. This architecture allows for flexible, maintainable, and
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

### Run monolith

You can run the Honeycomb Monolith either as a Dockerized container or as a standalone Spring Boot
service.

#### Run in a docker container

```shell
docker compose -f docker-compose-monolith.yml up --build -d
```

#### Run as a Spring Boot service

1. Start the required dependencies:

```shell
docker compose up -d
```

2. Run Spring Boot application

```shell
./gradlew :monolith:bootRun
```

### Accessing the Application

* The application will be accessible on http://localhost:8080.
* Swagger UI for API documentation can be accessed at http://localhost:8080/swagger-ui/index.html.

### Stop application

* To stop application running in a container,
  use `docker compose -f docker-compose-monolith.yml down -v`
* To dependencies, use `docker compose down -v`
* To stop Spring Boot application running in shell, kill the job via `control+c` or `Ctrl+c`

# Reference Architecture

## Example implementation

This project provides a reference architecture through implementation of the
[Opora API](https://tarasshablii.github.io/opora-api/).