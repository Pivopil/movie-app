# How To Run

## Pre-installations 
- Idea (google-style-format, terraform, nodejs plugins)
- Java 11 (OpenJdk) locally
- Docker client locally
- MySQL Workbench locally
- Install AWS CLI locally
- Generate AWS developer credentials with AdminAccess permissions
- Generate www.omdbapi.com API token
- Generate Github auth developer token
- Register in Terraform Cloud using free account from app.terraform.io
- (optional) Install Nodejs

### To run the app locally you

You could build and run the app using maven and Idea
There should be the next mandatory env variables to your run/debug configuration

```shell
SPRING_PROFILES_ACTIVE=dev
GOOGLE_CLIENT_ID=533437697750-qvhvl9ku0ae7tf1on686pg99vprf9s6q.apps.googleusercontent.com
DATABASE_URL=jdbc:mysql://YOUR_USERNAME:YOUR_PASSWORD@MYSQL_DOMAIN/movie_db
```
You are free to use your own GOOGLE_CLIENT_ID and any MySQL instance.

you could customize application profiles for your needs

```yaml
spring:
  datasource:
    url: ${DATABASE_URL}
    driver-class-name: com.mysql.jdbc.Driver
  jpa:
    hibernate.ddl-auto: none
  liquibase:
    enabled: true
  data:
    rest:
      base-path: /api
      default-page-size: 10
      return-body-on-update: false
      max-page-size: 50
google-client-id: ${GOOGLE_CLIENT_ID}
```

Be sure to provide correct migrations, because Hibernate DDL 
is disabled and schema structure is create and populated with initial data by Liquibase