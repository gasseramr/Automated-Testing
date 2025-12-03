Automated E-Commerce Website Testing (Selenium + Java + TestNG)

Overview
- Validates core e-commerce flows on Demoblaze using Selenium WebDriver, Java, and TestNG.
- Follows the Page Object Model (POM) for maintainability and reusability.
- Includes explicit waits, clean setup/teardown, and standard HTML/XML reports.

Target Demo Site
- Default: `https://www.demoblaze.com/` (login, products, cart, checkout, order confirmation)

Project Structure
- `src/main/java/`
  - `com.example.pages/` — Demoblaze Page Object classes (Home, LoginModal, ProductPage, CartPage, CheckoutModal)
  - `com.example.utils/` — Utilities (DriverFactory, ConfigReader, WaitUtils, ScreenshotUtil)
- `src/test/java/`
  - `com.example.tests/` — Demoblaze Test classes (DemoblazeLoginTests, DemoblazeProductTests, DemoblazeCartTests, DemoblazeCheckoutTests)
- `src/test/resources/`
  - `config.properties` — URL, credentials, browser type, waits
- `testng.xml` — Test suite configuration

Setup
- Prerequisites:
  - Java 17+
  - Maven 3.8+
  - Chrome/Firefox installed
- Dependencies:
  - Managed via `pom.xml` (Selenium, TestNG, WebDriverManager, optional ExtentReports)

Configuration
- Edit `src/test/resources/config.properties`:
  - `baseUrl=https://www.demoblaze.com/`
  - `browser=chrome` (or `firefox`)
  - `headless=false` (set `true` for CI)
  - `implicitWait=5`
  - `explicitWait=20`
  - `username=gasser`
  - `password=amr`

How to Run
- Run all tests:
  - `mvn clean test`
- Override browser/headless from command line:
  - `mvn -Dbrowser=firefox -Dheadless=true clean test`

Reports
- TestNG report: `target/surefire-reports/index.html` (and XML files)
- Screenshots on failure: `target/screenshots/`
- Optional: Extent report can be added later (`target/extent-report.html`).

Core Tests Included
- Login: valid and invalid credentials.
- Cart: add/remove items and verify counts.
- Checkout: fill details, finish, and verify order confirmation.

Extensibility
- Add new pages in `com.example.pages` and expose high-level actions only.
- Add new tests under `com.example.tests` and reference them in `testng.xml`.
- Support data-driven tests via TestNG `@DataProvider` if needed.

