# FinancePro

## Overview

FinancePro is a streamlined financial analysis system, expanding on the capabilities of StockTracker. It features a secure REST API with authentication and registration capabilities, supporting both traditional email + password and OAuth 2.0 (Gmail, Facebook, etc.) registration methods. The application is designed for deployment on an EKS cluster, providing a user-friendly public API, and is equipped with a robust CI/CD pipeline for seamless updates.

## Project Details

- **Technology Stack:**
  - Java 17
  - Spring
  - MySQL
  - AWS
  - OAuth 2.0

## Key Features

- **Authentication and Registration:**
  - Users can register using email + password or OAuth 2.0 (Gmail, Facebook, etc.), providing flexibility and user convenience.

- **EKS Deployment:**
  - Deployed within an EKS cluster, making the application accessible to the external world through a user-friendly API.

- **CI/CD Pipeline:**
  - Integrated a robust CI/CD pipeline for continuous updates, ensuring a smooth development and release process.

- **Stateless OAuth2 Authentication:**
  - Implemented stateless authentication through OAuth2, enhancing both security and user experience.

- **Unit Testing:**
  - Ensured application stability with thorough unit testing of the service layer.
