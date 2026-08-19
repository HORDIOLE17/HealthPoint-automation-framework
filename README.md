# HealthPoint Automation Framework

I built this project to practice designing a reusable Java automation framework instead of keeping API tests as isolated scripts.

The framework currently focuses on REST API testing with **Java 17, REST Assured, TestNG, and Maven**. I separated configuration, API requests, models, and test logic so the project can grow without putting everything inside the test classes.

## Tech Stack

* Java 17
* REST Assured
* TestNG
* Maven
* Git
* GitHub

## What Is Implemented

The current version includes:

* REST API test automation
* Reusable API client layer
* Centralized configuration
* Shared API test setup
* Java models for response data
* Status code validation
* Response body validation
* Health-check testing
* TestNG suite configuration
* Maven test execution

## Project Structure

```text
HealthPoint-automation-framework
│
├── src
│   ├── main
│   │   ├── java/com/healthpoint/automation
│   │   │   ├── clients
│   │   │   │   └── PostClient.java
│   │   │   ├── config
│   │   │   │   └── ConfigReader.java
│   │   │   └── models
│   │   │       └── Post.java
│   │   └── resources
│   │       └── config.properties
│   │
│   └── test
│       └── java/com/healthpoint/automation
│           ├── api
│           │   ├── GetPostAPITest.java
│           │   └── HealthCheckApiTest.java
│           └── base
│               └── BaseApiTest.java
│
├── pom.xml
├── testng.xml
└── README.md
```

## How the Framework Is Organized

I keep API request logic separate from the tests.

```text
Test
  ↓
PostClient
  ↓
REST Assured
  ↓
API
```

For example, `PostClient` handles communication with the Posts API, while the test class focuses on the scenario and validation.

This makes it easier to reuse request logic and maintain the framework as more tests are added.

## Main Components

### ConfigReader

`ConfigReader` loads values from `config.properties`.

I use it so values such as the base URL are not hardcoded inside every test.

### BaseApiTest

`BaseApiTest` contains common API setup.

It configures REST Assured before the tests run.

### PostClient

`PostClient` contains reusable request logic for Posts endpoints.

This keeps REST Assured request code out of individual test classes when the same logic can be reused.

### Post

`Post` is the Java model used to represent post data returned by the API.

## Configuration

The project currently uses the public JSONPlaceholder API.

```properties
base.url=https://jsonplaceholder.typicode.com
```

Configuration is stored in:

```text
src/main/resources/config.properties
```

Passwords, tokens, API keys, and other secrets should not be committed to the repository.

## Running the Tests

Requirements:

* Java 17+
* Maven

Run the full suite from the project root:

```bash
mvn clean test
```

Successful execution should finish with:

```text
BUILD SUCCESS
```

## Current Test Coverage

The framework currently includes:

* API health check
* GET post validation
* HTTP status validation
* Response data validation

I am continuing to expand the API layer with negative and additional CRUD scenarios.

## Next Steps

My next improvements are:

* Negative API testing
* POST, PUT, PATCH, and DELETE coverage
* Request and response logging
* Reusable assertions
* Test data handling

After strengthening the API layer, I plan to add:

* Selenium WebDriver
* Page Object Model
* SQL database validation
* Allure reporting
* Parallel execution
* GitHub Actions / Jenkins
* Docker support

## Why I Built It This Way

My goal is to build a framework that stays readable and maintainable as the test suite grows.

Instead of putting configuration, requests, data handling, and assertions into one class, I am separating those responsibilities into reusable components.

This project is still being developed, and I am adding each layer step by step while keeping the existing tests working.
