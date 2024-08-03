Jesus
# valuationservice
Calculates collateral value &amp; market value for given accounts, which hold certain positions.

# Overview

The Financial Services Valuation System is a Java-based application designed to compute the valuation of financial assets. The system integrates with various services to fetch data such as asset prices, foreign exchange rates, and eligibility information, and then calculates the valuation results for a set of positions. The application uses Spring Boot for application development and Reactor for reactive programming.

# Features
Fetches asset prices, foreign exchange rates, and eligibility information from external services. Calculates the valuation of financial assets in different currencies. Provides a reactive API for efficient data processing and response handling.

# Technologies Used
Java 11 Spring Boot Reactor (Spring WebFlux) Maven - Build and dependency management JUnit 5 - Unit testing Mockito - Mocking framework for testing

# Getting Started
Prerequisites Java 11 or later Maven 3.6.3 or later

# Installation
Clone the repository:

git clone https://github.com/Haridath-Bodapati/valuationservice.git
cd valuationservice

Build the project:
mvn clean install

Run the application:
mvn spring-boot:run Running Tests

To run the test suite, use the following command:
mvn test

# Usage
The application exposes a REST API that allows clients to request valuations for a set of account IDs and a target currency. For example, to calculate valuations, you can run the Junit.

Sample Data
To facilitate testing, the project includes mock implementations for services like PositionsService, EligibilityService, PriceService, and FXService.

# Contributing
We welcome contributions to the project! If you'd like to contribute, please follow these guidelines:

Fork the repository and create your branch from main. Commit your changes and push them to your fork. Create a pull request, describing the changes and why they are necessary.

# License
This project is licensed under the MIT License - see the LICENSE file for details.

# Contact
For any questions or suggestions, please reach out to:

Haridath Bodapati - haridath@gmail.com Project Repository - https://github.com/Haridath-Bodapati/valuationservice.git
