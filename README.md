# DevOps Final Project

## Overview

This project is the final assignment for the DevOps course, focusing on the implementation of essential DevOps practices. The project is divided into three parts:

1. **Continuous Integration and Deployment (CI/CD) Automation Server**: A Spring Boot application managing CI/CD jobs.
2. **Python Tests**: A set of Python tests to validate the CI/CD server.
3. **Continuous Deployment Pipeline Orchestration**: Orchestration of the CI/CD server and Python tests using Docker Compose in the Play with Docker environment.

## Table of Contents

- [Project Structure](#project-structure)
- [Part 1: CI/CD Automation Server](#part-1-cicd-automation-server)
- [Part 2: Python Tests](#part-2-python-tests)
- [Part 3: Continuous Deployment Pipeline Orchestration](#part-3-continuous-deployment-pipeline-orchestration)
- [Setup and Installation](#setup-and-installation)
- [How to Run](#how-to-run)
- [Contributing](#contributing)
- [License](#license)

## Project Structure

```
DevOps-Final-Project/
│
├── src/                      # Source code for the Spring Boot application
│   ├── main/
│   └── test/
├── tests/                    # Python test files
├── docker-compose.yml        # Docker Compose configuration file
├── Dockerfile                # Dockerfile for the Spring Boot application
└── README.md                 # Project documentation
```

## Part 1: CI/CD Automation Server

This part of the project involves developing a Spring Boot application that serves as a Continuous Integration and Deployment (CI/CD) automation server. The server provides various endpoints for CRUD operations on CI/CD jobs and manages job statuses.

### Features

- **CRUD Operations**: Create, read, update, and delete CI/CD jobs.
- **Database Integration**: Uses H2 Database for development and testing.
- **Password Encoding**: Secure password encoding with BCrypt.
- **Logging**: Configured logging for better traceability.
- **Testing**: Includes unit, integration, and exception tests.

### Endpoints

- `GET /jobs`: Retrieve all jobs.
- `POST /jobs`: Create a new job.
- `GET /jobs/{id}`: Retrieve a job by ID.
- `PUT /jobs/{id}`: Update a job.
- `DELETE /jobs/{id}`: Delete a job.
- `GET /jobs/status/{status}`: Retrieve jobs by status.
- `GET /jobs/jobType/{jobType}`: Retrieve jobs by job type.
- `GET /jobs/date-range`: Retrieve jobs by a date range.

## Part 2: Python Tests

This part involves writing Python tests to validate the CI/CD automation server. The tests are written using `pytest` and include integration tests, logging, and the use of fixtures to set up the test environment.

### Test Cases

- **test_get_all_jobs**: Ensure retrieval of all CI/CD jobs works correctly.
- **test_create_job**: Ensure creation of a new CI/CD job works correctly.
- **test_get_job_by_id**: Ensure retrieval of a specific CI/CD job by ID works correctly.
- **test_update_job_status**: Ensure updating the status of a CI/CD job works correctly.
- **test_delete_job**: Ensure deletion of a CI/CD job works correctly.

## Part 3: Continuous Deployment Pipeline Orchestration

In this part, the project is orchestrated using Docker Compose in the Play with Docker environment. The orchestration includes services for the CI/CD automation server, Redis, PostgreSQL, and the Python tests.

### Services

- **cd-server**: Spring Boot application.
- **redis**: Caching service.
- **db**: PostgreSQL database.
- **tester**: Python testing service.

## Setup and Installation

### Prerequisites

- **Java 11** or higher
- **Maven**
- **Docker**
- **Docker Compose**
- **Python 3.8** or higher
- **pip** (Python package installer)

### Installation Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/DevOps-Final-Project.git
   cd DevOps-Final-Project
   ```

2. Set up the Spring Boot application:
   ```bash
   cd src/main
   mvn clean install
   ```

3. Set up the Python environment:
   ```bash
   cd ../tests
   python3 -m venv venv
   source venv/bin/activate
   pip install -r requirements.txt
   ```

## How to Run

### Running the Spring Boot Application

```bash
cd src/main
mvn spring-boot:run
```

### Running the Python Tests

```bash
cd tests
pytest
```

### Running with Docker Compose

```bash
docker-compose up --build
```
