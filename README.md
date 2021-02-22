# Recruitment Application
An amusement park recruitment application where recruiters can hire
applicants for various jobs. It is hosted on [Heroku](https://dashboard.heroku.com/)
and is supported by at least the Google Chrome, Microsoft Edge, Safari, and Firefox
browsers.

Make sure to **carefully** read [CONTRIBUTING.md](CONTRIBUTING.md) before
writing any code.

## Table of Contents
 - [1. Repository Structure](#1-repository-structure)
 - [2. Frameworks Used](#2-frameworks-used)
 - [3. Dependencies](#3-dependencies)
 - [4. Build Instructions](#4-build-instructions)
   - [4.1. Using VS Code](#41-using-vs-code)
   - [4.2. Using Maven](#41-using-maven)

## 1. Repository Structure
The recruitment application is a maven project and thus uses the
following repository structure.
 - **src/main/java**: Contains source files, organized in layers following
                      domain-driven design.
 - **src/main/resources**: Contains resources such as thymeleaf templates and
                           properties for loggers, internationalization, etc.
 - **src/test**: Contains Junit tests for each source file, making use of
                      spring's mockMVC to simulate HTTP transactions.

## 2. Frameworks Used
The following is a list of the frameworks and technologies used throughout the project:
 - **Spring Boot**
 - **Maven**
 - **Thymeleaf**
 - **Heroku**
 - **Postgres**
 - **Jacoco**
 - **Junit**
 - **GitHub Actions**

## 3 Dependencies
The following _must_ be installed:
 - **JDK 11**: Needed to compile the project.

_Either_ of the following _must_ be installed:
 - **Visual Studio Code**: Can be used to compile and run tests using
                           its [java extension](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).
 - **maven**

## 4. Build Instructions
There a multiple approaches to build the project, the ways described
below are suggestions.

### 4.1 Using VS Code
 1. Open the project in VS Code.
 2. Open [`RecruitmentApplication.java`](src/main/java/se/kth/iv1201/group4/recruitment/RecruitmentApplication.java).
 3. Click "Run" above the `main` method.

The tests can be run by opening a test source file under [`src/test`](src/test/java/se/kth/iv1201/group4/recruitment)
and clicking "Run Test" above the class name.

### 4.2 Using Maven
 1. Navigate to the root of the repository.
 2. Run `mvn spring-boot:run`

The tests can be run using `mvn test`.
