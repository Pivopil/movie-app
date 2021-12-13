package io.github.pivopil.movieapp;

import io.github.pivopil.movieapp.models.Movie;
import io.github.pivopil.movieapp.repository.MovieRepository;
import io.github.pivopil.movieapp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieServiceTests {

  @Autowired private MovieService movieService;

  @Autowired private MovieRepository movieRepository;

  @Test
  void movieService_callGetTopTenRatedMovies_getCorrectResult() {

    // GIVEN: Data from H2 db/migrations/test, more than ten movies with ratings and box office
    List<Movie> topTenRatedMovies = movieService.getTopTenRatedMovies();

    // WHEN: user gets top ten rated movies ordered by box office value
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
            index -> assertEquals(
                topTenRatedMovies.get(index).getBoxOfficeValue(), boxOfficeValues.get(index)));
  }

  @Test
  void movieRepository_callGetFindAllByTitle_getMovieByTitleWithBestPictureOscarAwardFlag() {
    // GIVEN: Data from H2 db/migrations/test for 'Movie 1' 'Best Picture' Oscar Award bool flag

    // WHEN:
    List<Movie> movies = movieRepository.findAllByTitle("Movie 6");

    // THEN:
    assertTrue(movies.size() > 0, "Got any results for 'Movie 1' title movie from db");

    // THEN:
    assertTrue(movies.get(0).getIsBestPicture(), "Movie got 'Best Picture' Oscar Award");
  }

  @Test
  void movieService_createOrUpdateMovieRating_ratingScoreShouldBeUpdated() {

    // GIVEN: Data from H2 db/migrations/test, more than ten movies with ratings and box office
    List<Movie> movies = movieService.getTopTenRatedMovies();
    Movie movie = movies.get(0);
    Long id = movie.getId();
    Long ratingScore = movie.getRatingScore();

    // WHEN:
    movieService.createOrUpdateMovieRating("user1", id, 99);
    movies = movieService.getTopTenRatedMovies();

    // THEN:
    movies.stream()
        .filter(it -> it.getId().equals(id))
        .findFirst()
        .map(Movie::getRatingScore)
        .ifPresent(
            updatedRatingScore ->
                assertNotEquals(
                    ratingScore,
                    updatedRatingScore,
                    "Movie scores are different before and after update"));
  }
}
