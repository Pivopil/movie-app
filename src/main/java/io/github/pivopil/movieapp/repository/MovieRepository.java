package io.github.pivopil.movieapp.repository;

import io.github.pivopil.movieapp.models.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

  @Query(
      "select new io.github.pivopil.movieapp.models.Movie(r.movie.id, m.title, m.year, m.isBestPicture, m.boxOfficeValue, sum(r.score)/count(r.userId)) from Rating r inner join Movie m on r.movie.id = m.id group by r.movie.id order by sum(r.score)/count(r.userId) DESC")
  List<Movie> getTopRatedMovies(Pageable pageable);

  Movie findMovieByTitle(String title);
}
