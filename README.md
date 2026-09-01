# Multi-Layer Test Automation Framework

[![Automation CI](https://github.com/HORDIOLE17/HealthPoint-automation-framework/actions/workflows/tests.yml/badge.svg)](https://github.com/HORDIOLE17/HealthPoint-automation-framework/actions/workflows/tests.yml)
[![Publish Allure Report](https://github.com/HORDIOLE17/HealthPoint-automation-framework/actions/workflows/allure-pages.yml/badge.svg)](https://github.com/HORDIOLE17/HealthPoint-automation-framework/actions/workflows/allure-pages.yml)

Java-based SDET portfolio framework covering **API, UI, and database validation** with parallel execution, cross-browser testing, automated reporting, GitHub Actions CI, and containerized browser execution.

The framework uses independent public demo targets so each automation layer can be exercised realistically: SauceDemo for browser workflows, JSONPlaceholder for REST API validation, and an in-memory H2 database for SQL/data-layer scenarios. The goal is to demonstrate reusable test architecture and engineering practices rather than model one production application.

The same suite can run locally, in GitHub Actions, or through Docker using `RemoteWebDriver` with Selenium standalone/grid-compatible browser containers.

## Execution Evidence

The regression suite contains **15 automated tests** and is validated in GitHub Actions against both **Chrome** and **Firefox**.

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

Allure reports include test results, environment metadata, feature/story annotations, severity, descriptions, failure evidence, and historical trend data across published runs.

**Live report:** [Open the latest Allure report](https://hordiole17.github.io/HealthPoint-automation-framework/)

The report is regenerated automatically after a successful `main` CI run and published from the `gh-pages` branch.

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
multi-layer-test-automation-framework
|
|-- .github/
|   `-- workflows/
|       |-- tests.yml
|       `-- allure-pages.yml
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

> The existing Java package namespace is intentionally retained to avoid a cosmetic package migration that would not change framework behavior.

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
<suite name="Multi-Layer Test Automation Suite"
       parallel="methods"
       thread-count="2">
```

WebDriver instances are isolated using `ThreadLocal<WebDriver>`, preventing parallel UI tests from sharing browser sessions.

## Retry and Failure Handling

Transient test failures are handled with `RetryAnalyzer` and `RetryTransformer`. Retries are intentionally limited to one additional attempt so genuine defects are not hidden.

When a UI test fails, the TestNG listener captures a browser screenshot and attaches it to Allure.

## Reporting

Generate the Allure report locally with:

```bash
mvn allure:report
```

Reports include features and stories, severity, descriptions, execution results, environment metadata, UI failure screenshots, and historical trend data in the published report.

GitHub Actions uploads browser-specific Allure results and generated HTML reports as workflow artifacts. After a successful `main` run, a separate publishing workflow downloads the Chrome results, restores prior Allure history, generates a fresh report, and updates the `gh-pages` branch.

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
             Allure Artifacts
                     |
           successful main run
                     |
                     v
           Publish Allure Report
                     |
                     v
                 gh-pages
```

For each browser, the pipeline configures Java 17 and the selected browser, runs the full Maven/TestNG suite, generates an Allure HTML report, and uploads browser-specific results and report artifacts.

The publishing workflow restores previous Allure history before regenerating and publishing the latest report.

Current browser matrix status:

```text
Chrome  : PASS
Firefox : PASS
```

## Docker / Remote Browser Execution

Docker Compose provides separate Chrome and Firefox Selenium standalone environments. These containers expose WebDriver endpoints used by `RemoteWebDriver` and are compatible with Selenium Grid-style remote execution without claiming a distributed hub/node topology.

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

![Remote Browser Execution](docs/images/selenium-grid.png)

## Running Locally

Requirements: Java 17+, Maven, and Chrome or Firefox.

```bash
mvn clean test
```

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

Runtime configuration is stored in `src/main/resources/config.properties`. The repository uses disposable demo/test configuration and an in-memory H2 database. Environment variables can override browser and remote WebDriver settings. Real credentials, tokens, and secrets should never be committed to the repository.

## Engineering Highlights

- Multi-layer API, UI, and database automation
- Reusable REST Assured client architecture
- API contract and negative validation
- Page Object Model and explicit UI synchronization
- Chrome and Firefox cross-browser execution
- Thread-safe WebDriver management
- Parallel TestNG execution
- Automatic retry and failure screenshot handling
- Allure reporting with retained history and trends
- GitHub Actions browser matrix and automated live report publishing
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
