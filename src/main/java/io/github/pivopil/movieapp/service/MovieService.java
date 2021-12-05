package io.github.pivopil.movieapp.service;

import io.github.pivopil.movieapp.models.Movie;
import io.github.pivopil.movieapp.models.Rating;
import io.github.pivopil.movieapp.models.RatingId;
import io.github.pivopil.movieapp.repository.MovieRepository;
import io.github.pivopil.movieapp.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

  public static final PageRequest TOP_TEN = PageRequest.of(0, 10);
  private final MovieRepository movieRepository;

  private final RatingRepository ratingRepository;

  public List<Movie> getTopTenRatedMovies() {
    return movieRepository.getTopRatedMovies(TOP_TEN).stream()
        .sorted(Movie::compareTo)
        .sorted(Collections.reverseOrder())
        .collect(Collectors.toList());
  }

  @Transactional
  public void createOrUpdateMovieRating(String userId, Long movieId, Integer score) {
    movieRepository
        .findById(movieId)
        .ifPresent(
            movie -> {
              Optional<Rating> rating = ratingRepository.findById(new RatingId(userId, movieId));
              if (rating.isPresent()) {
                rating.ifPresent(it -> it.setScore(score));
              } else {
                ratingRepository.save(new Rating(movie, userId, score));
              }
            });
  }
}
