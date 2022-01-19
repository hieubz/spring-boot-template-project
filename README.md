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
- [x] Swagger
- [ ] MultiThread
- [ ] Background Job
- [ ] Token based Authentication
- [ ] Authorization
- [ ] Retry
- [ ] MDC logging
- [ ] Monitoring with Prometheus
- [ ] Sentry