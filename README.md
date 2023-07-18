# Microservices with spring boot

Monorepo with an application built with microservices architecture.

## Architecture

<p align="center">
  <img src="" />
</p>


## Gateway

The gateway uses the following dependencies:

- Gateway (to route to API)
- Eureka Discovery Client (REST based service to read config)
- Config Client (Read from config server)
- Spring Boot Actuator (Operation and Expose metrics)

## Discovery Server

The discovery server needs:

- Eureka Server (for the discovery server)
- Config Client (for reading from config)
- Spring Boot Actuator

## Config Server

The Config server needs the following dependencies

- Config Sever

## Worker

The Worker microservice needs the following dependencies: 

- DB driver (MySQL, PostgreSQL, etc)
- Lombok
- Spring Data JPA
- Spring Web
- Config client
- Eureka Discovery Client
- Spring Boot Actuator

### Process of building

1. After creating the gateway, discovery server and config server, add them to the project via **File/new/module from existing sources**
2. Configure the ```application.properties``` or ```application.yml``` file.
3. Create and import the worker microservice
4. Config the worker microservice (including JDBC properties to connect to the DB) 
5. Create entity inside worker class with the annotations: 
   - @Entity (jpa)
   - @Getter (lombok)
   - @Setter (lombok)
   - @AllArgsConstructor (lombok)
   - @NoArgsConstructor (lombok)
   - @Builder (lombok)
   - @Id (jpa)
6. Create repository interface (dao) to extend JPA Repository
7. Create the service (interface and implementation)
8. Create the REST controller for employee