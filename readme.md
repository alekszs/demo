# Springboot Demo Application

Web Shop

## Requirements

For building and running the application you need:

- [JDK 11+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Gradle 5+](https://gradle.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.example.demo.DemoApplication` class from your IDE.

Alternatively you can use the [Spring Boot Gradle plugin](https://spring.io/guides/gs/gradle/) like so:

```shell
gradle bootRun
```

## Database

H2 Console: http://localhost:8080/h2-ui
    JDBC URL: jdbc:h2:mem:testdb

`resources.demoDataSet.json` contains a data set which is being inserted at the start-up by `com.example.demo.config.DatabaseChangelog`

changelog.switch in `application.properties` can be used to enable or disable the initial database population

## Swagger

Swagger UI: http://localhost:8080/swagger-ui.html