## Assumptions
- Use MySQL8 database (Easy to start)
- Maybe NoSQL is an option, also I see that data about movies is read only, so it could be cached for common queries)
- Use Spring Boot (Maybe it could be codeless using AWS ApiGateway to DynamoDB integration)
- Using Liquibase
- Use Hibernate Mappings to be able to use different SQL databases, like MySQL for production and H2 for tests
- Use Google Jwt token for API protection - no need to store any user related data anywhere, only user id is used
- Use end-to-end deployment using AWS stack to speedup development and get out-of-the-box services based on free-tiers
- Use Docker and AWS ECS with fargate as a chip container orchestration solution