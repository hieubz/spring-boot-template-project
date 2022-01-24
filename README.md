# Spring Boot - Base Backend Project

built by SonPM, DatDD, [phamduyhieu.com](https://phamduyhieu.com)

## Architecture
We divided the project into 4 layers:

- **Application**: Rest APIs, passing user input into services
- **Core**: domain logic, divided into services
- **Repository**: layer for interaction with models and performing DB operations
- **Infrastructure**: support 3 higher layers

## Features

- [x] Basic REST API
- [x] Multiple DataSource Configuration
- [x] Read/Write Splitting
- [x] Caching with Redis
- [x] Caching Local
- [x] Multiple Cache Manager
- [x] MDC logging
- [x] Exception Handling
- [x] Token based Authentication
- [ ] Session based Authentication
- [x] Swagger
- [x] MultiThread
- [ ] Background Job
- [ ] Authorization
- [ ] Retry
- [ ] Monitoring with Prometheus
- [ ] Sentry

## Setup:
- Requirements: MySQL, Redis
- Update your database config in **application.properties**
- Install [Redis Commander](https://github.com/joeferner/redis-commander) which will support you to check your Redis cache
- Run `redis-commander --redis-db 10 --redis-password 123` (depends on your db/password config)

## Notes:
- Check your swagger: http://localhost:8080/swagger-ui.html#/
- Redis Commander UI: http://127.0.0.1:8081