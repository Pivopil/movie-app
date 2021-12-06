## How to Test

You could run test using maven without any additional dependencies, 
because H2 database is used for tests

Only basic scenarios are covered by now, so you are free to extend the tests to increase code coverage

There is file with [test properties](https://github.com/Pivopil/movie-app/blob/main/src/test/resources/application.yaml)

```yaml
spring:
  datasource:
    url: "jdbc:h2:mem:movie_db;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
    driver-class-name: org.h2.Driver
  jpa:
    hibernate.ddl-auto: none
  liquibase:
    enabled: true
    drop-first: true
    change-log: classpath:/db/changelog/db.changelog-test.yaml
google-client-id: some-id
```

Tests have independent migration scripts and SQL syntax could be different for other databases, 
for now H2 and MySQL8 are supported