package io.github.pivopil.movieapp.api;

import io.github.pivopil.movieapp.api.model.RatingScore;
import io.github.pivopil.movieapp.models.Movie;
import io.github.pivopil.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping(value = "/api/movies/top")
  public List<Movie> getTopTenRatedMovies() {
    return movieService.getTopTenRatedMovies();
  }

  @PatchMapping(value = "/api/movies/{movieId}/rating")
  public void createOrUpdateMovieRating(
      Principal principal, @PathVariable Long movieId, @RequestBody RatingScore ratingScore) {
    movieService.createOrUpdateMovieRating(principal.getName(), movieId, ratingScore.getScore());
  }
}
