package io.github.pivopil.movieapp.api;

import io.github.pivopil.movieapp.models.Movie;
import io.github.pivopil.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping(value = "/api/movies/top")
  public List<Movie> getTopTenRatedMovies() {
    return movieService.getTopTenRatedMovies();
  }
}
