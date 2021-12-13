package io.github.pivopil.movieapp.api;

import io.github.pivopil.movieapp.api.model.RatingScore;
import io.github.pivopil.movieapp.models.Movie;
import io.github.pivopil.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping(value = "/api/movies/top")
  public List<Movie> getTopTenRatedMovies() {
    return movieService.getTopTenRatedMovies();
  }

  @PostMapping(value = "/api/movies/{movieId}/rating")
  public void createOrUpdateMovieRating(
      Principal principal,
      @PathVariable @NotNull @Min(1) Long movieId,
      @RequestBody @Validated RatingScore ratingScore) {
    movieService.createOrUpdateMovieRating(principal.getName(), movieId, ratingScore.getScore());
  }
}
