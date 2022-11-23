# Spring Boot Based Backend Project

Thanks to my brothers **SonPM**, **DatDD**, **LinhPH** at GHTK.

## Overview
A project which contains _**common backend features**_ to help me or any other Spring Boot developers can code faster.

## Architecture
We divided the project into 4 components:

    .
    ├── application               # Controllers, passing user input into services respectively
    ├── core                      # Domain logic, divided into services
    ├── infrastructure            # Config, repository, third parties,...
    └── shared                    # constants, DTO, utils,...

And we have the code flow as shown below:

![Raw Design](src/main/resources/META-INF/raw_code_flow.png)

## Features

- [x] Basic REST API
- [x] MultiThread, Async
- [x] Event Handling
- [x] Race Condition Handling (Redis/MongoDB Lock)
- [ ] Distributed Transactions
- [x] Multiple DataSource Configuration
- [x] Read/Write Splitting
- [x] Caching with Redis
- [x] Caching Local with Caffeine
- [x] Multiple Cache Manager
- [x] MDC logging
- [x] Exception Handling
- [x] Fixed Token Authentication
- [x] JWT Authentication
- [ ] Session based Authentication
- [ ] Authorization
- [x] Swagger
- [x] Background Job
- [x] Monitoring with Prometheus
- [x] Sentry Integration
- [x] Spring JDBC, Spring JPA
- [x] Pagination
- [x] REST client using OpenFeign
- [x] Retry
- [ ] Kafka Producer/Consumer (Json, Avro)

## Setup:
- Run `docker-compose up -d` to start MySQL and Redis (or update your database config in **application.properties**)
- Run **_resources/schema/init-schema.sql_** to initialize your MySQL database.
- Run commands in **_resources/schema/mongo_schema.txt_** to initialize your MongoDB database.
- Install [Redis Commander](https://github.com/joeferner/redis-commander) which will support you to check your Redis cache
- Run `redis-commander --redis-db 10 --redis-password 123` (depends on your db/password config)

## Notes:
- Check your swagger: http://localhost:8080/swagger-ui/index.html#/
- Redis Commander UI: http://127.0.0.1:8081