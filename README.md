Course Management System API

The Course Management System API is an online management application that aims to facilitate efficient interaction 
between students and instructors during the submission of assignments and receiving feedback from instructors.

Installation

To run the project, follow these steps:
1. Clone the project using the following URL: https://github.com/yuraolkhovskyi
2. Specify the database configurations (contact the project owner for more information)
3. Specify the profile as 'dev' and try running the project.

The project uses the following technologies:
1. Spring Boot 3.0
2. PostgreSQL
3. JPA/ORM - Hibernate
4. Database Migration tool - Flyway
5. Token-based authentication - JWT
6. Software management and comprehension tool - Maven
7. Docker containerization
8. Fully follows the REST specification.

Testing
The project includes the following types of tests:
1. Unit tests for Service layer
2. API unit tests for Controller layer
3. End-to-end (e2e) tests - the application-api profile is used for running API tests
4. Integration tests for DAO layer - running using virtual H2 db. The configuration is in the application.yml file.
5. The application fully follows the REST specification.

Deployment
The application is deployed to AWS using a CI/CD pipeline. The CI/CD pipeline configuration can be found in the root of the project 
in a file called 'buildspec.yml' 

When a new commit is pushed to the 'master' branch, the docker image of the application is sent to ECR, 
and the app is deployed in a cluster which is using a task definition that pulls and uses the latest docker image.

All configuration related to Docker can be found in the root of the project in the dockerfile configuration file.