# HealthPoint Automation Framework

[![HealthPoint Automation CI](https://github.com/HORDIOLE17/HealthPoint-automation-framework/actions/workflows/tests.yml/badge.svg)](https://github.com/HORDIOLE17/HealthPoint-automation-framework/actions/workflows/tests.yml)

Java-based test automation framework covering API, UI, and database validation with parallel execution, cross-browser testing, automated reporting, CI/CD, and containerized browser execution.

The project is structured around reusable framework components and separation of concerns. The same suite can run locally, in GitHub Actions, or through Docker with Selenium Grid / RemoteWebDriver.

## Tech Stack

- Java 17
- Selenium WebDriver
- REST Assured
- TestNG
- Maven
- H2 Database
- Allure Report
- GitHub Actions
- Docker & Docker Compose
- Selenium Grid / RemoteWebDriver
- WebDriverManager
- AssertJ
- Jackson

## Current Test Suite

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0
```

The complete suite is validated in GitHub Actions against both **Chrome** and **Firefox**.

### API Testing

- API health check
- GET request validation
- Negative GET / 404 validation
- POST / create validation
- PUT / update validation
- DELETE validation
- Response contract validation
- Response field type validation
- Negative empty-body validation
- Status code and response body assertions

### UI Testing

- Valid login validation
- Invalid credential validation
- Locked-out user validation
- Inventory page validation
- Product sorting validation
- Logout flow validation
- Page Object Model
- Explicit wait-based synchronization
- Thread-safe WebDriver lifecycle

### Database Testing

- H2 database setup
- SQL query execution
- Test data seeding
- Positive patient record validation
- Negative nonexistent-record validation
- PreparedStatement usage
- Field-level assertions

## Architecture

```text
                         TestNG
                            |
             +--------------+--------------+
             |              |              |
             v              v              v
         API Tests       UI Tests       DB Tests
             |              |              |
             v              v              v
        PostClient      Page Objects   DatabaseUtils
             |              |              |
             v              v              v
       REST Assured     DriverFactory      H2
                            |
                  +---------+---------+
                  |                   |
                  v                   v
            Local Driver        RemoteWebDriver
                  |                   |
           +------+-----+       Selenium Grid
           |            |             |
        Chrome       Firefox    +------+------+ 
                              Chrome       Firefox
```

## Project Structure

```text
HealthPoint-automation-framework
|
|-- .github/
|   `-- workflows/
|       `-- tests.yml
|
|-- src/
|   |-- main/
|   |   |-- java/com/healthpoint/automation/
|   |   |   |-- clients/
|   |   |   |-- config/
|   |   |   |-- driver/
|   |   |   |-- models/
|   |   |   |-- pages/
|   |   |   `-- utils/
|   |   `-- resources/
|   |
|   `-- test/
|       |-- java/com/healthpoint/automation/
|       |   |-- api/
|       |   |-- base/
|       |   |-- db/
|       |   |-- listeners/
|       |   |-- retry/
|       |   `-- ui/
|       `-- resources/
|
|-- Dockerfile
|-- docker-compose.yml
|-- pom.xml
|-- testng.xml
`-- README.md
```

## Framework Design

The framework separates test scenarios from implementation details.

```text
API: Test -> PostClient -> REST Assured -> API
UI:  Test -> Page Object -> DriverFactory -> WebDriver
DB:  Test -> DatabaseUtils -> SQL -> H2
```

This keeps HTTP request handling, browser lifecycle management, page interactions, database access, and assertions in separate layers.

## Cross-Browser Driver Management

`DriverFactory` supports:

- Chrome and Firefox
- local WebDriver execution
- headless execution in CI
- RemoteWebDriver execution through Selenium Grid
- runtime browser selection through the `BROWSER` environment variable
- runtime grid selection through `SELENIUM_REMOTE_URL`
- `ThreadLocal<WebDriver>` isolation for parallel tests

Unsupported browser values fail fast with a clear exception.

## Parallel Execution

TestNG runs test methods in parallel:

```xml
<suite name="HealthPoint Automation Suite"
       parallel="methods"
       thread-count="2">
```

WebDriver instances are isolated with:

```java
ThreadLocal<WebDriver>
```

This prevents parallel UI tests from sharing a browser session.

## Retry and Failure Handling

The framework includes TestNG-based transient failure handling through:

- `RetryAnalyzer`
- `RetryTransformer`

Retries are intentionally limited to one additional attempt so genuine failures are not hidden.

When a UI test fails, the TestNG listener captures a browser screenshot and attaches it to Allure.

## Allure Reporting

Allure is integrated with Maven and TestNG.

Generate a report locally:

```bash
mvn allure:report
```

Reports include:

- Features
- Stories
- Severity
- Test descriptions
- Execution results
- Environment metadata
- Failure screenshots for UI failures

## CI/CD

A single GitHub Actions workflow executes the complete suite on pushes and pull requests to `main` using a browser matrix:

```text
             Pull Request / Push
                     |
                     v
             GitHub Actions
                     |
              +------+------+
              |             |
              v             v
           Chrome        Firefox
              |             |
              +------+------+
                     |
               mvn clean test
                     |
                     v
                Allure Report
                     |
                     v
             Browser Artifacts
```

For each browser, the pipeline:

1. configures Java 17 and the selected browser;
2. runs the full Maven/TestNG suite;
3. generates an Allure HTML report;
4. uploads raw Allure results;
5. uploads the browser-specific Allure HTML artifact.

Current cross-browser CI status:

```text
Chrome  : PASS
Firefox : PASS
```

## Docker / Selenium Grid Execution

Docker Compose provides separate Chrome and Firefox Selenium environments.

```text
Automation Test Container
          |
          | RemoteWebDriver
          v
Selenium Standalone Browser
     Chrome or Firefox
```

Chrome profile:

```bash
docker compose --profile chrome up --build
```

Firefox profile:

```bash
docker compose --profile firefox up --build
```

Remote endpoints inside the Docker network are:

```text
Chrome  -> http://selenium-chrome:4444
Firefox -> http://selenium-firefox:4444
```

This separates the automation runtime from the browser runtime and provides reproducible browser execution.

## Running Locally

Requirements:

- Java 17+
- Maven
- Chrome or Firefox

Run the default configured browser:

```bash
mvn clean test
```

Run with an explicit browser:

```bash
BROWSER=chrome mvn clean test
```

```bash
BROWSER=firefox mvn clean test
```

Expected suite result:

```text
Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Configuration

Runtime configuration is stored in:

```text
src/main/resources/config.properties
```

Example:

```properties
base.url=https://jsonplaceholder.typicode.com
```

Environment variables can override runtime browser and Selenium Grid execution settings.

Credentials, tokens, and other secrets should not be committed to the repository.

## Key Engineering Features

- Multi-layer API, UI, and database automation
- Reusable REST Assured API client architecture
- API contract and negative validation
- Page Object Model
- Explicit UI synchronization
- Chrome and Firefox cross-browser execution
- Thread-safe WebDriver management
- Parallel TestNG execution
- Automatic retry handling
- Failure screenshot attachments
- Allure reporting and environment metadata
- Unified GitHub Actions CI/CD browser matrix
- Browser-specific CI artifacts
- Dockerized Chrome and Firefox execution
- RemoteWebDriver / Selenium Grid support
- Local, CI, and containerized execution modes
- PreparedStatement-based database validation

## Future Improvements

- Parameterized application environments
- External test data management
- Additional end-to-end business workflows
- API schema validation using formal JSON Schema files
- Cloud-based browser execution
- Test result trend/history publishing

## Execution Evidence

### Allure Test Results

The suite contains API, UI, and database validation with automated Allure reporting.

![Allure Suites](docs/images/allure-suites.png)

### GitHub Actions CI

GitHub Actions executes the test suite automatically and publishes Allure artifacts.

![GitHub Actions](docs/images/github-actions.png)

### Dockerized Execution

The automation framework can run from a dedicated Maven container against standalone Selenium browser containers.

![Docker Containers](docs/images/docker-containers.png)

### Selenium Grid

UI automation supports RemoteWebDriver execution through Selenium standalone/grid-compatible browser containers.

![Selenium Grid](docs/images/selenium-grid.png)
