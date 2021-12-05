package io.github.pivopil.movieapp.models;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "rating")
@IdClass(RatingId.class)
public class Rating {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "movie_id")
  private Movie movie;

  @Id private String userId;

  private Integer score;
}
