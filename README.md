# Java SDET Test Automation Framework

[![HealthPoint Automation CI](https://github.com/HORDIOLE17/HealthPoint-automation-framework/actions/workflows/tests.yml/badge.svg)](https://github.com/HORDIOLE17/HealthPoint-automation-framework/actions/workflows/tests.yml)

Java-based test automation framework covering **API, UI, and database validation** with parallel execution, cross-browser testing, automated reporting, GitHub Actions CI, and containerized browser execution.

HealthPoint is a portfolio automation framework built to demonstrate reusable SDET engineering patterns across independent public demo targets. SauceDemo is used for browser workflows, JSONPlaceholder for REST API validation, and an in-memory H2 database for SQL/data-layer scenarios. These targets are intentionally independent; the project demonstrates framework architecture rather than representing a single production healthcare application.

The same test suite can run locally, in GitHub Actions, or through Docker using `RemoteWebDriver` with Selenium standalone/grid-compatible browser containers.

## Execution Evidence

The current regression suite contains **15 automated tests** and is validated in GitHub Actions against both **Chrome** and **Firefox**.

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0
```

### GitHub Actions

The CI workflow executes the full suite on every push and pull request to `main` using a Chrome/Firefox browser matrix.

![GitHub Actions](docs/images/github-actions.png)

### Allure Results

Allure reports include test results, environment metadata, feature/story annotations, severity, descriptions, and failure evidence.

![Allure Suites](docs/images/allure-suites.png)

### Dockerized Browser Execution

The automation runtime can execute against standalone Selenium browser containers through `RemoteWebDriver`.

![Docker Containers](docs/images/docker-containers.png)

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
- RemoteWebDriver
- WebDriverManager
- AssertJ
- Jackson

## Test Coverage

### API

- API health check
- GET validation
- Negative GET / 404 validation
- POST / create validation
- PUT / update validation
- DELETE validation
- Response contract validation
- Response field type validation
- Negative empty-body validation
- Status code and response body assertions

### UI

- Valid login
- Invalid credentials
- Locked-out user
- Inventory page validation
- Product sorting
- Logout flow
- Page Object Model
- Explicit wait-based synchronization
- Thread-safe WebDriver lifecycle

### Database

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
           +------+-----+       Selenium Browser
           |            |             Container
        Chrome       Firefox      Chrome / Firefox
```

The framework separates test scenarios from implementation details:

```text
API: Test -> PostClient -> REST Assured -> API
UI:  Test -> Page Object -> DriverFactory -> WebDriver
DB:  Test -> DatabaseUtils -> SQL -> H2
```

This keeps HTTP request handling, browser lifecycle management, page interactions, database access, and assertions in separate layers.

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

## Browser and Driver Management

`DriverFactory` supports:

- Chrome and Firefox
- local WebDriver execution
- headless execution in CI
- `RemoteWebDriver` execution against Selenium standalone/grid-compatible containers
- runtime browser selection through the `BROWSER` environment variable
- runtime remote endpoint selection through `SELENIUM_REMOTE_URL`
- `ThreadLocal<WebDriver>` isolation for parallel tests

Unsupported browser values fail fast with a clear exception.

## Parallel Execution

TestNG runs test methods in parallel:

```xml
<suite name="HealthPoint Automation Suite"
       parallel="methods"
       thread-count="2">
```

WebDriver instances are isolated using:

```java
ThreadLocal<WebDriver>
```

This prevents parallel UI tests from sharing browser sessions.

## Retry and Failure Handling

Transient test failures are handled with:

- `RetryAnalyzer`
- `RetryTransformer`

Retries are intentionally limited to one additional attempt so genuine defects are not hidden.

When a UI test fails, the TestNG listener captures a browser screenshot and attaches it to Allure.

## Reporting

Generate the Allure report locally with:

```bash
mvn allure:report
```

Reports include:

- Features and stories
- Severity
- Test descriptions
- Execution results
- Environment metadata
- Failure screenshots for UI failures

GitHub Actions also uploads browser-specific Allure results and generated HTML reports as workflow artifacts.

## Continuous Integration

GitHub Actions runs the complete suite for pushes and pull requests to `main` using a browser matrix:

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
                CI Artifacts
```

For each browser, the pipeline:

1. configures Java 17 and the selected browser;
2. runs the full Maven/TestNG suite;
3. generates an Allure HTML report;
4. uploads raw Allure results;
5. uploads the browser-specific Allure HTML artifact.

Current browser matrix status:

```text
Chrome  : PASS
Firefox : PASS
```

## Docker / Remote Browser Execution

Docker Compose provides separate Chrome and Firefox Selenium standalone environments. These containers expose WebDriver endpoints used by `RemoteWebDriver` and are compatible with Selenium Grid-style remote execution without claiming a distributed hub/node topology.

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

Remote endpoints inside the Docker network:

```text
Chrome  -> http://selenium-chrome:4444
Firefox -> http://selenium-firefox:4444
```

Remote browser execution evidence:

![Remote Browser Execution](docs/images/selenium-grid.png)

## Running Locally

Requirements:

- Java 17+
- Maven
- Chrome or Firefox

Run the default configured browser:

```bash
mvn clean test
```

Run explicitly on Chrome:

```bash
BROWSER=chrome mvn clean test
```

Run explicitly on Firefox:

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

The repository uses only disposable demo/test configuration. The H2 database runs in memory, so no production database credentials are required. Environment variables can override browser and remote WebDriver settings.

Real credentials, tokens, and secrets should never be committed to the repository.

## Engineering Highlights

- Multi-layer API, UI, and database automation
- Reusable REST Assured client architecture
- API contract and negative validation
- Page Object Model
- Explicit UI synchronization
- Chrome and Firefox cross-browser execution
- Thread-safe WebDriver management
- Parallel TestNG execution
- Automatic retry handling
- Failure screenshot attachments
- Allure reporting and environment metadata
- GitHub Actions browser matrix
- Browser-specific CI artifacts
- Dockerized Chrome and Firefox execution
- RemoteWebDriver support
- PreparedStatement-based database validation
- Local, CI, and containerized execution modes

## Future Improvements

- Parameterized application environments
- External test data management
- Additional end-to-end business workflows
- Formal JSON Schema validation
- Cloud-based browser execution
- Published Allure test-history and trend reporting
