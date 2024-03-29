# movie-app
Movie Demo App

## Main components

![movie-app](movie-app.jpg)


## Real usage cost without loading (100 request per day) in [AWS ECS](https://github.com/Pivopil/movie-app-aws-ecs)

### 8 am, Dec 6 - 11 pm, Dec 9, 2021 - up to 100 h in total


![img_1.png](img_1.png)

### Cost projection for the month

![img_2.png](img_2.png)

### Quick Start
- [Go to the landing page](https://pivopil.github.io/)
- Use Google Login Button to get Google Id Jwt token
- Use any rest client to interact with APIs, like this

#### Get ten top-rated movies ordered by box office value
```shell
curl 'https://springbootapp.ecs.awsdevbot.com/api/movies/top' \
 --header 'Content-Type: application/json' \
 --header 'Authorization: GOOGLE_ID_TOKEN'
```

#### Set rating for the movie
```shell
curl --location --request POST 'https://springbootapp.ecs.awsdevbot.com/api/movies/16/rating' \
  --data-raw '{"score": 77 }' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: GOOGLE_ID_TOKEN'
```

#### Get Movie by title with Best Picture Oscar (be sure to replace spaces with the plus symbol)
```shell
curl 'https://springbootapp.ecs.awsdevbot.com/api/movies/search/findAllByTitle?title=Avatar' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: GOOGLE_ID_TOKEN'
```

### Java App Overview
- [Github repo](https://github.com/Pivopil/movie-app)
- [Generate template using Spring Initializr](https://start.spring.io/)
- Java 11
- Spring (Boot, Security, MVC, JPA, REST, Actuator, Validation, ...)
- Maven
- MySQL 8
- H2
- Liquibase
- Docker
- [Google Identity Java SDK for the integrations with Google IAM](https://developers.google.com/identity/sign-in/web/backend-auth)
- [Spring Data Jpa REST](https://spring.io/guides/gs/accessing-data-rest/)
- [Spring Security Oauth2 JWT](https://waynestalk.com/en/spring-security-google-signin-jwt-jpa-explained-en/)
- [Spring Security](https://www.javadevjournal.com/spring/securing-a-restful-web-service-with-spring-security/)

### MySQL8 Database schema

![db.png](db.png)

#### Movie table

Movie table contains [prepared](https://github.com/Pivopil/movie-app/tree/main/data_loader)
- **year** - got this data from CSV file
- **title** - got this data from CSV file
- **is_best_picture** - got this data from CSV file
- **box_office_value** - the box office, got this value from [www.omdbapi.com](https://www.omdbapi.com) at the data preparation step using 'year' and 'title' from csv

#### Rating table

- **user_id** - 'sub' claim from Google Id token 
- **movie_id** - foreign key for Movie id
- **score** - value from 0 to 99, given by user
- primary key includes both user_id and movie_id, so user could create or update only one, personal score

### Frond End App Overview
- [Static content with index page are hosted on Github](https://pivopil.github.io/)
- [Repository with code](https://github.com/Pivopil/pivopil.github.io)
- HTML5
- CSS3
- Bulma
- [Google Identity JS SDK](https://developers.google.com/identity/gsi/web/guides/display-button#javascript)

### CI CD pipeline and infrastructure Overview
- [Repo with movie-app terraform](https://github.com/Pivopil/movie-app-aws-ecs)
- [Terraform Cloud](https://app.terraform.io/)
- Github Web Hooks for the integration with AWS Code Pipeline
- AWS Cloud
- Route53
- ACM
- AWS MySQL RDS
- Code Build
- Code Deploy
- ECS Fargate
- ALB
- CloudWatch
- S3
- ECR

### Movie Data Preparation App
- [Simple CSV to SQL converter with HTTP client](https://github.com/Pivopil/movie-app/tree/main/data_loader)
- Nodejs
- www.omdbapi.com public API (used to get box office value)