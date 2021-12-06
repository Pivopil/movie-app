FROM 080252646890.dkr.ecr.us-east-1.amazonaws.com/openjdk:alpine

EXPOSE 8080
ADD ./target/movie-app-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "-Xms1024m", "-Xmx1800m", "app.jar"]
