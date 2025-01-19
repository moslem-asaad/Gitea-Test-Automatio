# Automated Testing for Gitea Functionalities

## Overview
This repository contains Java-based automated tests for verifying key functionalities of the Gitea platform. The tests ensure a robust user experience by validating critical workflows and integrating automation into the CI/CD pipeline.

### Covered Functionalities
1. **Sign In**: Ensures secure and reliable user authentication.
2. **Create Repository**: Verifies repository creation with accurate settings.
3. **Add File in Repository**: Tests the addition of files to ensure smooth content management.

---

## Testing Strategy and Objectives

### Objectives
- Validate UI and functional behavior across critical workflows.
- Automate regression testing for key paths.
- Integrate tests with the CI/CD pipeline to enhance quality assurance.

### Scope
- **Included**:
  - All fields and flows in Sign In, Create Repository, and Add File functionalities.
- **Excluded**:
  - Non-critical features like repository cloning and performance testing.

### Testing Types
- Functional Testing
- UI Testing
- Integration Testing
- Automation Testing (using Selenium WebDriver in Java)
- API Testing

---

## Setup Instructions

### Prerequisites
1. **Java Development Kit (JDK)**: Version 19 or later.
2. **Maven**: For dependency management and test execution.
3. **Selenium WebDriver**: Pre-configured drivers for Chrome and Firefox.
4. **Gitea Server**: A test instance of Gitea.

## Running the Tests

### UI Tests (GitHub Actions Workflow: `ui.yaml`)

#### Inputs
- `grid_url` (string, required): Grid to run tests on. Defaults to `http://localhost:4444`.
- `chrome_131_0` (boolean, optional): Run tests on Chrome (131.0).
- `firefox_133_0` (boolean, optional): Run tests on Firefox (133.0).

#### Locally
Run individual test suites using Maven:
```bash
mvn test -Dtest=TestSignIn
mvn test -Dtest=TestCreateRepository
mvn test -Dtest=TestAddFile
```

#### GitHub Actions
To execute UI tests via GitHub Actions:
1. Trigger the workflow manually under the **Actions** tab.
2. Provide inputs for `grid_url` and the browsers to test (`chrome_131_0`, `firefox_133_0`).

### API Tests (GitHub Actions Workflow: `api.yaml`)

#### Workflow Execution
1. Trigger the workflow manually under the **Actions** tab.
2. Ensure the `GITEA_API_TOKEN` secret is defined in the repository settings.

#### Locally
Run API tests:
```bash
mvn clean test -Dtest=**/apiTests/*
```

---

## Testing Workflow

1. **Preparation**:
   - Tests are designed for modular execution (Sign In, Repository Creation, File Addition).
   - Test data (e.g., credentials, repository names) is configurable.
2. **Execution**:
   - Validates each critical functionality.
3. **Defect Logging**:
   - Test failures are logged with stack traces.
4. **Regression**:
   - Automated regression tests are triggered on every code update (will be added).

---

## Reporting

- Test reports are generated in the `/target/surefire-reports` directory.
  


## Key Features
- **Automation Framework**: Built with Selenium and Java.
- **Cross-Browser Testing**: Chrome and Firefox supported.
- **CI/CD Ready**: Integrated with GitHub Actions for seamless test automation.
- **Modular Design**: Tests are reusable and easily extendable.

---

## Contribution Guidelines
1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature"
   ```
4. Push the branch:
   ```bash
   git push origin feature-name
   ```
5. Create a pull request.

---
