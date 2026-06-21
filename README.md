# QA Practice Platform - Selenium Automation

This repository contains Selenium WebDriver automation tests for a local E-Commerce QA practice platform.

The project validates the main user journeys of the application such as registration, login, product search, filtering, cart actions, checkout, and UI validations.

## Tech Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Allure Report
- Page Object Model
- MySQL validation support when needed

## Application Under Test

Base URL:

```txt
http://localhost:3000
```

Main features covered:

- User registration
- User login
- Product search
- Product filtering
- Add product to cart
- Cart total calculation
- Checkout flow
- Error message validation

## Project Structure

```txt
src/test/java
│
├── tests
│   ├── LoginTests.java
│   ├── RegistrationTests.java
│   ├── SearchTests.java
│   ├── CartTests.java
│
├── pages
│   ├── BasePage.java
│   ├── LoginPage.java
│   ├── RegistrationPage.java
│   ├── SearchPage.java
│   ├── CartPage.java
│
├── driver
│   ├── DriverFactory.java
│   ├── BrowserType.java
│
├── utils
│   ├── Waits.java
│   ├── ScreenshotHelper.java
│
└── config
    ├── TestConfig.java
    ├── PropertyLoader.java
```

## Test Coverage

### Registration

| Test Case | Description |
|---|---|
| Positive registration | Register user with valid data |
| Duplicate email registration | Validate duplicate email error |
| Empty required fields | Validate required field messages |
| Invalid email format | Validate email format error |

### Login

| Test Case | Description |
|---|---|
| Positive login | Login with valid email and password |
| Invalid login | Login with wrong email or password |
| Missing login fields | Validate email/password required messages |
| Logout | Validate user can logout successfully |

### Search and Filter

| Test Case | Description |
|---|---|
| Search valid product | Validate product appears in search result |
| Search invalid product | Validate no results message |
| Filter by category | Validate products belong to selected category |
| Clear filters | Validate all products appear again |

### Cart and Checkout

| Test Case | Description |
|---|---|
| Add item to cart | Validate product is added successfully |
| Add multiple items | Validate multiple products in cart |
| Cart calculation | Validate total price calculation |
| Checkout | Validate successful order creation |

## How to Run

Install dependencies:

```bash
mvn clean install
```

Run tests:

```bash
mvn clean test
```

Run with TestNG suite:

```bash
mvn clean test -DsuiteXmlFile=suite1.xml
```

## Allure Report

Generate report:

```bash
allure generate target/allure-results --clean -o target/allure-report
```

Open report:

```bash
allure open target/allure-report
```

Or serve report directly:

```bash
allure serve target/allure-results
```

## Example Locator Strategy

Example for finding an Add to Cart button using product name:

```java
By addToCartButton = By.xpath(
    "//h2[contains(text(),'Laptop Stand')]/ancestor::article//button[contains(text(),'Add to cart')]"
);
```

## Framework Notes

- Page Object Model is used to separate locators and actions from test classes.
- TestNG is used for test execution and assertions.
- Explicit waits are preferred over `Thread.sleep()`.
- Screenshots can be attached to Allure reports on failure.
- Test data can be managed from properties files or external test data files.

## Author

Mohanad Mohamed

QA Automation Engineer
