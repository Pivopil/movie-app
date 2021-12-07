package io.github.pivopil.movieapp.models;

import com.google.api.client.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "rating")
@IdClass(RatingId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "movie_id")
  private Movie movie;

  @Id private String userId;

  private Integer score;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Rating)) return false;
    Rating rating = (Rating) o;
    if (Objects.isNull(movie) || Objects.isNull(movie.getId()) || Strings.isNullOrEmpty(userId))
      return false;
    return movie.equals(rating.getMovie()) && userId.equals(rating.getUserId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(movie, userId);
  }

  @Override
  public String toString() {
    return this.getClass().getName() + "[movie_id=" + movie + ", user_id=" + userId + "]";
  }
}
