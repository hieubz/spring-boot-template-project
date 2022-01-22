# Spring Boot - Base Backend Project

built by SonPM, DatDD, [phamduyhieu.com](https://phamduyhieu.com)

## Architecture
We divided the project into 4 layers:

- **Application**: Rest APIs, passing user input to services
- **Core**: domain logic, divided into services
- **Repository**: layer for interaction with models and performing DB operations
- **Infrastructure**: support 3 higher layers

## Features

- [x] Basic REST API
- [x] Multiple DataSource Configuration
- [x] Read/Write Splitting
- [x] Caching with Redis
- [ ] Caching Local 
- [ ] Multiple Cache Manager
- [x] Token based Authentication
- [ ] Session based Authentication
- [x] Swagger
- [ ] MultiThread
- [ ] Background Job
- [ ] Authorization
- [ ] Retry
- [x] MDC logging
- [ ] Monitoring with Prometheus
- [ ] Sentry

## Setup:
- Requirements: MySQL, Redis
- Update your database config in application.properties
- Install [Redis Commander](https://github.com/joeferner/redis-commander) which will support you to check your Redis cache
- Run `redis-commander --redis-db 10 --redis-password 123` (depends on your db/password config)

## Notes:
- Check your swagger: http://localhost:8080/swagger-ui.html#/
- Redis Commander UI: http://127.0.0.1:8081