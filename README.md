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
