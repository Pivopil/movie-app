package io.github.pivopil.movieapp;

import io.github.pivopil.movieapp.models.Movie;
import io.github.pivopil.movieapp.repository.RatingRepository;
import io.github.pivopil.movieapp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MovieAppTests {

  @Autowired private MovieService movieService;

  @Autowired RatingRepository ratingRepository;

  @Test
  void getTopRatedMoviesOrderedByBoxOffice() throws Exception {

    // GIVEN: Data from H2 db/migrations/test, more then 10 movies with ratings and box office value

    // WHEN: user gets top ten rated movies ordered by box office value
    List<Movie> topTenRatedMovies = movieService.getTopTenRatedMovies();
    List<Integer> boxOfficeValues =
        movieService.getTopTenRatedMovies().stream()
            .map(Movie::getBoxOfficeValue)
            .sorted(Integer::compareTo)
            .sorted(Collections.reverseOrder())
            .collect(Collectors.toList());

    // THEN:
    assertEquals(topTenRatedMovies.size(), 10, "Got ten movie items");

    // THEN:
    topTenRatedMovies.forEach(
        it -> assertNotNull(it.getRatingScore(), "Each movie item has not null ratingScore"));

    // THEN: check the order
    IntStream.range(0, topTenRatedMovies.size())
        .forEach(
            index -> {
              assertEquals(
                  topTenRatedMovies.get(index).getBoxOfficeValue(), boxOfficeValues.get(index));
            });
  }
}
