package io.github.pivopil.movieapp.service;

import io.github.pivopil.movieapp.models.Movie;
import io.github.pivopil.movieapp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;

  public List<Movie> getTopTenRatedMovies() {
    return movieRepository.getTopRatedMovies(PageRequest.of(0, 10)).stream()
        .sorted(Movie::compareTo)
        .sorted(Collections.reverseOrder())
        .collect(Collectors.toList());
  }
}
