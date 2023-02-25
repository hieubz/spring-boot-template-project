# Spring Boot Based Backend Project

Thanks to my brothers **SonPM**, **DatDD**, **LinhPH**.

## Overview
A project which contains _**common backend features**_ to help me or any other Spring Boot developers can code faster.

_Java 11, Spring Boot 2.7.x_

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

#### Authen/Authorization
- [x] Fixed Token Authentication
- [x] JWT Authentication
- [ ] Session based Authentication
- [ ] Authorization

#### Web Basics
- [x] Basic REST API
- [x] Spring JDBC, Spring JPA
- [x] Pagination
- [x] MDC logging
- [x] Exception Handling
- [x] Jackson & JSR310
- [x] REST client with OpenFeign
- [ ] SOAP client with JAXB
- [x] Background Job
- [x] Retry
- [x] ActiveMQ and Spring JMS Integration
- [x] Export XLSX/CSV reports
- [ ] CORS configuration
- [x] Swagger

#### Advanced Techniques
- [x] Caching with Redis
- [x] Caching Local with Caffeine
- [x] Multiple Cache Manager
- [x] MultiThread, Async
- [x] Event Handling
- [x] Race Condition Handling (Redis/MongoDB Lock)
- [ ] Distributed Transactions
- [x] Kafka Producer/Consumer (Json, Avro)
- [x] Search Backend with Elasticsearch
- [x] Generate and send emails
- [ ] Rate limit with Bucket4j

#### Datasource
- [x] Multiple DataSource Configuration
- [x] Read/Write Splitting

#### Unit Test
- [x] Unit Test common cases (with Junit 4, Mockito, PowerMock)


#### Monitoring
- [x] Monitoring with Actuator & Prometheus
- [x] Sentry Integration


## How to use
Firstly, clone it to your local and open by your IDE (IntelliJ, Eclipse).

Each commit will be a feature except **update README.md** commits. For example, you can search on **IntelliJ** (**Git** tab) by feature keywords:

![img_2.png](src/main/resources/META-INF/feature_searching.png)

Then you can apply the code of the corresponding commit for your feature. 

## Setup

To run this project (optional for new guys, **minimum 16GB RAM**)

- Download and install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Recommends)
- Install [Docker and Docker Compose](https://linuxhint.com/install-docker-compose-ubuntu-22-04/) if you don't have your own database and message brokers
- Run `git clone https://github.com/hieubz/spring-boot-based-project.git`
- [Configure](https://www.jetbrains.com/help/idea/sdk.html#change-project-sdk) **JDK 11** for your project on **IntelliJ IDEA**
- Run `docker-compose up -d` to start **MySQL**, **MongoDB**, **Redis**, **Kafka**, **ActiveMQ** and **Elasticsearch** (or update your database configs in **application.properties**)
- Run **_resources/schema/mysql-schema.sql_** to initialize your MySQL database.
- Run commands in **_resources/schema/mongo-schema.txt_** to initialize your MongoDB database.
- Run your **DemoApplication** and check Swagger UI: http://localhost:8080/swagger-ui/index.html#/

## Tools

#### Redis Commander

- Install [Redis Commander](https://github.com/joeferner/redis-commander) which will support you to check your Redis cache
- Run `redis-commander --redis-db 10 --redis-password 123` (depends on your db/password config)
- Check your Redis Commander UI: http://127.0.0.1:8081

#### DataGrip

- I recommend using [DataGrip](https://www.jetbrains.com/datagrip/download/) to work with MySQL and MongoDB
