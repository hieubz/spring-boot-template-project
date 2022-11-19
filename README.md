# Spring Boot Based Backend Project

built by SonPM, DatDD, [phamduyhieu.com](https://phamduyhieu.com)

## Architecture
We divided the project into 4 layers:

    .
    ├── application               # Controllers, passing user input into services respectively
    ├── domain                    # Domain logic, divided into services
    ├── infrastructure            # Config, repository, third parties,...
    └── shared                    # constants, DTO, utils,...


## Features

- [x] Basic REST API
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
- [x] Pagination
- [x] REST client using OpenFeign
- [x] Retry
- [x] Event Handling
- [ ] Kafka Producer/Consumer (Json, Avro)
- [x] MultiThread
- [ ] Race Condition Handling (Redis Lock)
- [ ] Distributed Transactions

## Setup:
- Run `docker-compose up -d` to start MySQL and Redis (or update your database config in **application.properties**)
- run _init-schema.sql_ to initialize your database.
- Install [Redis Commander](https://github.com/joeferner/redis-commander) which will support you to check your Redis cache
- Run `redis-commander --redis-db 10 --redis-password 123` (depends on your db/password config)

## Notes:
- Check your swagger: http://localhost:8080/swagger-ui.html#/
- Redis Commander UI: http://127.0.0.1:8081