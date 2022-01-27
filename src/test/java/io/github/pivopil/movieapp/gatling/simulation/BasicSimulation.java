package io.github.pivopil.movieapp.gatling.simulation;

import java.util.concurrent.ThreadLocalRandom;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BasicSimulation extends Simulation {

  int movieId = ThreadLocalRandom.current().nextInt(10, 251);

  String token = System.getenv("GOOGLE_JWT_ID_TOKEN");

  HttpProtocolBuilder httpProtocol =
      http.baseUrl("https://springbootapp.ecs.awsdevbot.com/api/movies")
          .acceptHeader("application/json");

  ScenarioBuilder scn =
      scenario("Scenario Name")
          .exec(
              http("Get top ten movies")
                  .get("/top")
                  .header("Authorization", token)
                  .header("Content-Type", "application/json"))
          .pause(1)
          .exec(
              http("Set rating")
                  .post("/" + movieId + "/rating")
                  .header("Authorization", token)
                  .header("Content-Type", "application/json")
                  .body(StringBody("{ \"score\": 70 }")));

  {
    setUp(scn.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
  }
}
