
## Introduction
In this project i implement cart system with microservice style 
i use some concepts in this project such as hexagonal architecture and cqrs


## Purpose
The purpose of this project is to simulate order drink with topping and get amount with some calculation like discount price

## Requirements
- java 17
- kafka
- mongodb 
- cassandra
- maven


## Modules
this project has multiple modules

- starbux-product-cmd --> this module is responsible to create and update and delete products in mongodb
- starbux-product-query --> this module is responsible to fetch products data with some endpoints
- starbux-cart --> this module is responsible to create cart with products and calculate price
- starbux-order --> this module is responsible to finalize order with payment -- but for simplicity this module didn't implement

## Build

- Maven
  you can use provided maven cmd in the root of the project to build and run this project
```shell 
.\mvnw clean install
```
## Test
to test this project you can use maven test command to run provided tests in test package
```shell 
.\mvnw clean test
```

## Run
- Docker -
  you can run docker compose of root folder to build and run this project.
```shell
docker-compose up -d
```



