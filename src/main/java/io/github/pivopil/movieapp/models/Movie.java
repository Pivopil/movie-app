package io.github.pivopil.movieapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Movie implements Comparable<Movie> {

  public Movie(
      long id,
      String title,
      int year,
      boolean isBestPicture,
      int boxOfficeValue,
      long ratingScore) {
    this.id = id;
    this.title = title;
    this.year = year;
    this.isBestPicture = isBestPicture;
    this.boxOfficeValue = boxOfficeValue;
    this.ratingScore = ratingScore;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;

  private String title;

  private Integer year;

  private Boolean isBestPicture;

  private Integer boxOfficeValue;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
  private List<Rating> ratings;

  @Transient private Long ratingScore;

  @Override
  public int compareTo(Movie movie) {
    if (getBoxOfficeValue() == null || movie.getBoxOfficeValue() == null) {
      return 0;
    }
    return getBoxOfficeValue().compareTo(movie.getBoxOfficeValue());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Movie)) return false;
    Movie movie = (Movie) o;
    if (id == null) return false;
    return id.equals(movie.getId());
  }

  @Override
  public int hashCode() {
    return Objects.nonNull(id) ? id.hashCode() : super.hashCode();
  }

  @Override
  public String toString() {
    return this.getClass().getName() + "[id=" + id + "]";
  }
}
