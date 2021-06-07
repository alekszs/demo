# Springboot Demo Application

## Requirements

For building and running the application you need:

- [JDK 16](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Gradle 7](https://gradle.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.example.demo.DemoApplication` class from your IDE.

Alternatively you can use the [Spring Boot Gradle plugin](https://spring.io/guides/gs/gradle/) like so:

```shell
gradle bootRun
```

## Database

H2 

`resources.demoDataSet.json` contains a data set which is being inserted at the start-up by `com.example.demo.config.DatabaseChangelog`

## Swagger

API Docs: /api-docs

Swagger UI: /swagger-ui.html